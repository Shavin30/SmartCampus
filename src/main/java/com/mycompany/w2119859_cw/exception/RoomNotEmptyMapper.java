package com.mycompany.w2119859_cw.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;

@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", "The room is currently occupied by active hardware.");
        error.put("errorCode", 409);

        return Response.status(Response.Status.CONFLICT) 
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
