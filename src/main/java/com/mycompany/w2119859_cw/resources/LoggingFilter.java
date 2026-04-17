package com.mycompany.w2119859_cw.resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider // This tells JAX-RS to apply this to all resources automatically
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Log incoming request details
        LOG.info("---- Incoming Request ----");
        LOG.info("Method: " + requestContext.getMethod());
        LOG.info("Path: " + requestContext.getUriInfo().getPath());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Log outgoing response details
        LOG.info("---- Outgoing Response ----");
        LOG.info("Status: " + responseContext.getStatus());
        LOG.info("--------------------------");
    }
}