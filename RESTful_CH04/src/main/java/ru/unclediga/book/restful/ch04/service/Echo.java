package ru.unclediga.book.restful.ch04.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/echo")
public class Echo {
    @GET
    public Response getEcho() {
        return Response.ok().build();
    }
}
