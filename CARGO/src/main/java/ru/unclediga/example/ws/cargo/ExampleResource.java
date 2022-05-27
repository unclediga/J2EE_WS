package ru.unclediga.example.ws.cargo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleResource {

    @Path("/add")
    @POST
    @Consumes("application/json")
    public Response addExample(){
        return Response.ok("ADD Example").build();
    }
    @Path("/get/{id}")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getExample(@PathParam("id") int ExampleId){
        return Response.ok("GET  Example[" + ExampleId + "]").build();
    }
    @Path("/put")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response putExample(){
        return Response.ok("PUT Example").build();
    }
    @Path("/del/{id}")
    @DELETE
    @Produces("text/plain")
    public Response delExample(@PathParam("id") int id){
        return Response.ok("DEL Example[" + id + "]").build();
    }

}
