package com.mycompany.w2119859_cw.resources;

import com.mycompany.w2119859_cw.repository.MockDatabase;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @GET
    public Response ping(){
        System.out.println("Current Room Count: " + MockDatabase.ROOMS.size());
        
        return Response
                .ok("ping")
                .build();
    }
}
