package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.exception.SensorUnavailableException;
import com.mycompany.w2119859_cw.model.Sensor;
import com.mycompany.w2119859_cw.model.SensorReading;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class SensorReadingResource {

    private String sensorId;
    private GenericRepository<Sensor> sensorRepo = new GenericRepository<>(MockDatabase.SENSORS);
    private GenericRepository<SensorReading> readingRepo = new GenericRepository<>(MockDatabase.READINGS);

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        return readingRepo.getAll().stream()
                .filter(r -> r.getId().contains(sensorId) || r.getId().startsWith("READ"))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading, @Context UriInfo uriInfo) {
        Sensor sensor = sensorRepo.getById(sensorId);

        // Existing availability logic
        if (sensor != null && !"ACTIVE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor " + sensorId + " is currently " + sensor.getStatus());
        }

        readingRepo.add(reading);

        // Build the Location URI
        java.net.URI uri = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();

        // Minimal Response: Return only the ID
        java.util.Map<String, String> responseBody = new java.util.HashMap<>();
        responseBody.put("id", reading.getId());

        return Response.created(uri)
                .entity(responseBody)
                .build();
    }
}
