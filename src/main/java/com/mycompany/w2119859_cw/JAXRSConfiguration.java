package com.mycompany.w2119859_cw;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("/api/v1") // Required by CW spec
public class JAXRSConfiguration extends Application {
    
}
