package ru.unclediga.book.restful.ch03.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;

public interface CustomerResource {
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    Response createCustomer(InputStream in);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    StreamingOutput getCustomer(@PathParam("id") int id);
}
