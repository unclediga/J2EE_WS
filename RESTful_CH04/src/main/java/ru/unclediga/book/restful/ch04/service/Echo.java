package ru.unclediga.book.restful.ch04.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/echo")
public class Echo {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEcho() {
        return Response.ok("ECHO from app root").build();
    }
}
