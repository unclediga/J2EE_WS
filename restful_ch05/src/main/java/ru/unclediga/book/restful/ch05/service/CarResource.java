package ru.unclediga.book.restful.ch05.service;


import javax.ws.rs.*;

@Path("/cars")
public class CarResource {

    static enum Color {
        BLACK,
        WHITE,
        RED,
        GREEN,
        BLUE
    }


    @GET
    @Path("/echo")
    @Produces("text/plain")
    public String getEcho(){
        return "send ECHO from @Path(/echo)";
    }


    @GET
    @Path("/p{p1}-{p2}")
    @Produces("text/plain")
    public String getManyPathParams(@PathParam("p1") int p1,
                                    @PathParam("p2") String p2) {
        if (p1 == 0 || p2 == null) {
            throw new WebApplicationException("not enough params");
        }
        return "GET:p1=" + p1 + ", p2=" + p2;
    }
}
