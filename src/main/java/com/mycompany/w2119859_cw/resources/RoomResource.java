package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.exception.RoomNotEmptyException;
import com.mycompany.w2119859_cw.model.Room;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/rooms")
public class RoomResource {

    private GenericRepository<Room> roomRepo = new GenericRepository<>(MockDatabase.ROOMS);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomRepo.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room, @Context UriInfo uriInfo) {

        // 1. Basic validation
        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"errorMessage\":\"Request body is missing or invalid.\", \"errorCode\": 400}")
                    .build();
        }

        // 2. ID Generation logic (consistent with your BaseModel)
        if (room.getId() == null || room.getId().trim().isEmpty()) {
            room.setId("ROOM-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        // 3. Use your existing roomRepo (not roomDAO)
        roomRepo.add(room);

        // 4. Build the URI for the Location header
        java.net.URI uri = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();

        // 5. Minimal Response: Return only the ID as requested for extra marks
        java.util.Map<String, String> responseEntity = new java.util.HashMap<>();
        responseEntity.put("id", room.getId());

        return Response.created(uri) // Sets 201 Created and Location header
                .entity(responseEntity) // Returns only the ID in the body
                .build();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = roomRepo.getById(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found with ID: " + roomId)
                    .build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = roomRepo.getById(roomId);

        // 1. Enhanced 404 logic with JSON body
        if (room == null) {
            java.util.Map<String, Object> error = new java.util.HashMap<>();
            error.put("errorMessage", "Room not found with ID: " + roomId);
            error.put("errorCode", 404);

            return Response.status(Response.Status.NOT_FOUND)
                    .type(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                    .entity(error)
                    .build();
        }

        // 2. Safety Logic (409 Conflict)
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("The room is currently occupied by active hardware.");
        }

        roomRepo.delete(roomId);
        return Response.noContent().build(); // 204 Success
    }
}
