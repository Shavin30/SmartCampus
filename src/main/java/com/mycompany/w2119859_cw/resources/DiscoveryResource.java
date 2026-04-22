package com.mycompany.w2119859_cw.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDiscovery() {
        Map<String, Object> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("description", "Smart Campus Sensor & Room Management API");
        
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        links.put("sensor Readings", "/api/v1/sensors/{sensorId}/readings");
        
        response.put("links", links);
        return response;
    }
}