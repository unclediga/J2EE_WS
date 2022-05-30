package ru.unclediga.book.restful.ch04.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/customers")
public class CustomerDatabaseResource {
    protected FirstLastCustomerResource russia = new FirstLastCustomerResource();
    protected CustomerResource europe = new CustomerResource();

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
    @Path("echo")
    public Response getEcho() {
        return Response.ok("ECHO from /customers").build();
    }
}


