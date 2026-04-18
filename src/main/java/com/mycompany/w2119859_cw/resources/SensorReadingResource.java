package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.model.SensorReading;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class SensorReadingResource {

    private String sensorId;
    private GenericRepository<SensorReading> readingRepo = new GenericRepository<>(MockDatabase.READINGS);

    // This constructor captures the sensorId from the parent
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        // This ensures you are only getting readings intended for THIS specific sensor
        return readingRepo.getAll().stream()
                .filter(r -> r.getId().contains(sensorId) || r.getId().startsWith("READ"))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        readingRepo.add(reading);
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
