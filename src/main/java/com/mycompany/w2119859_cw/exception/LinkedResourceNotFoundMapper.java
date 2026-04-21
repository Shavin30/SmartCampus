package com.mycompany.w2119859_cw.exception;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", ex.getMessage());
        error.put("errorCode", 422); 

        return Response.status(422) 
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
