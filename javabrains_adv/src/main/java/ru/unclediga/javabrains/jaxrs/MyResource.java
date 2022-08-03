package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }   

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public MyData getData() {
        return new MyData("Got it!");
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public MyData insData(MyData data) {
        data.setValue("POST:" + data.getValue());
        return data;
    }

    @POST
    @Path("rest")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response insData2(MyData data, @Context UriInfo info) {
        data.setValue("POST:" + data.getValue());
        final Response response = Response.created(info.getRequestUri()).entity(data).build();
        return response;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updData(MyData data, @PathParam("id") long id) {
        data.setValue("PUT("+id+"):" + data.getValue());
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updData2(MyData data) {
        data.setValue("PUT(x):" + data.getValue());
    }

    @GET
    @Path("/rest")
    @Produces(MediaType.TEXT_PLAIN)
    public String getId() {
        return "Got rest!";
    }

    @GET
    @Path("/rest/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getId(@PathParam("id") String id) {
        return "Got " + id + "!";
    }

}
