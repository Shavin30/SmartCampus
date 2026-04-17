package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.exception.RoomNotEmptyException;
import com.mycompany.w2119859_cw.model.Room;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rooms")
public class RoomResource {

    // Using the GenericRepository to manage Room entities
    private GenericRepository<Room> roomRepo = new GenericRepository<>(MockDatabase.ROOMS);

    /**
     * Part 2.1: GET / - Provide a comprehensive list of all rooms.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomRepo.getAll();
    }

    /**
     * Part 2.1: POST / - Enable the creation of new rooms.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        roomRepo.add(room);
        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    /**
     * Part 2.1: GET /{roomId} - Fetch detailed metadata for a specific room.
     */
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

    /**
     * Part 2.2: DELETE /{roomId} - Implement room decommissioning. Note: Safety
     * logic for active sensors will be added in the next step.
     */
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = roomRepo.getById(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Safety Logic: Check if the room has sensors
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Cannot delete room " + roomId + " because it still has active sensors.");
        }

        roomRepo.delete(roomId);
        return Response.noContent().build();
    }
}
