package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.model.SensorReading;
import com.mycompany.w2119859_cw.repository.GenericRepository;
import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        // Returns the list of historical data for the campus
        return readingRepo.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        readingRepo.add(reading);
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}