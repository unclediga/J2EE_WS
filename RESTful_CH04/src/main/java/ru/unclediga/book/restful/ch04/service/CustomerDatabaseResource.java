package ru.unclediga.book.restful.ch04.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/customers")
public class CustomerDatabaseResource {
    private FirstLastCustomerResource russia = new FirstLastCustomerResource();
    private CustomerResource europe = new CustomerResource();

    @Path("{database}-db")
    public Object getDatabase(@PathParam("database") String db) {
        if (db.equals("europe")) {
            return europe;
        } else if (db.equals("russia")) {
            return russia;
        } else {
            return null;
        }
    }

    @GET
    @Path("echo2")
    public Response getEcho() {
        return Response.ok().build();
    }
}


