package com.mycompany.w2119859_cw.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", ex.getMessage());
        error.put("errorCode", 503); 
        
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}