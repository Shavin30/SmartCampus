package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.model.Sensor;
import com.mycompany.w2119859_cw.model.Room;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import com.mycompany.w2119859_cw.exception.LinkedResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/sensors")
public class SensorResource {

    private GenericRepository<Sensor> sensorRepo = new GenericRepository<>(MockDatabase.SENSORS);
    private GenericRepository<Room> roomRepo = new GenericRepository<>(MockDatabase.ROOMS);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type != null && !type.trim().isEmpty()) {
            return sensorRepo.getAll().stream()
                    .filter(s -> s.getType() != null
                    && s.getType().trim().equalsIgnoreCase(type.trim()))
                    .collect(Collectors.toList());
        }
        return sensorRepo.getAll();
    }
 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {
        Room room = roomRepo.getById(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Cannot link sensor: Room ID " + sensor.getRoomId() + " not found.");
        }

        sensorRepo.add(sensor);
        room.getSensorIds().add(sensor.getId());

        java.net.URI uri = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();

        java.util.Map<String, String> responseBody = new java.util.HashMap<>();
        responseBody.put("id", sensor.getId());

        return Response.created(uri)
                .entity(responseBody)
                .build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
