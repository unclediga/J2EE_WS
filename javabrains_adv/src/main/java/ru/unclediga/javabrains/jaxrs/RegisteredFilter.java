package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class RegisteredFilter implements ContainerResponseFilter, ClientResponseFilter {
    protected final static String CONTAINER_HEADER_NAME = "X-Registered-Container";
    protected final static String CLIENT_HEADER_NAME = "X-Registered-Client";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(CONTAINER_HEADER_NAME, "Y");
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(CLIENT_HEADER_NAME, "Y");
    }
}
