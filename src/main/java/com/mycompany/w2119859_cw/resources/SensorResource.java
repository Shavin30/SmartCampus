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

@Path("/sensors") // Distinct path for sensor-specific operations
public class SensorResource {

    private GenericRepository<Sensor> sensorRepo = new GenericRepository<>(MockDatabase.SENSORS);
    private GenericRepository<Room> roomRepo = new GenericRepository<>(MockDatabase.ROOMS);

    /**
     * Part 3.2: GET / - List all sensors. Supports filtering by type (e.g.,
     * /sensors?type=Temperature)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type != null && !type.isEmpty()) {
            return sensorRepo.getAll().stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return sensorRepo.getAll();
    }

    /**
     * Part 3.1: POST / - Create a sensor and link it to a room.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        // Validation: Does the target room exist?
        Room room = roomRepo.getById(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Cannot link sensor: Room ID " + sensor.getRoomId() + " not found.");
        }

        sensorRepo.add(sensor);

        // Update the Room's internal list to maintain the relationship
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    /**
     * Part 4: Sub-resource locator. This handles any request starting with
     * /sensors/{sensorId}/readings
     */
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        // We pass the sensorId to the sub-resource constructor
        return new SensorReadingResource(sensorId);
    }
}
