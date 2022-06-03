package ru.unclediga.book.restful.ch05.service;


import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;

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
    public String getEcho() {
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
        return "par1[" + p1 + "] par2[" + p2 + "]";
    }

    @GET
    @Path("/firm{p1}/year{p2:\\d{4}}")
    @Produces("text/plain")
    public String getPathRegExp(@PathParam("p1") String p1,
                                @PathParam("p2") int p2) {
        return "p1[" + p1 + "] p2[" + p2 + "]";
    }

    @GET
    @Path("/firm{p1}/{many:.*}/year{p2}")
    @Produces("text/plain")
    public String getPathInTheMiddle(@PathParam("p1") String p1,
                                     @PathParam("p2") int p2,
                                     @PathParam("many") String many) {
                                     // @PathParam("many") PathSegment many NOT WORKED!!!!
        // a/b/c    PathSegment  -> getPath()/toString() return "c"
        // a/b/c    String       -> return "a/b/c"
        return "p1[" + p1 + "] many[" + many + "] p2[" + p2 + "]";
    }

    //"http://localhost:7778/cars/hyundai/getz;color=blue;engine=1_4/interior;color=black/2010")
    @GET
    @Path("/{firm}/{model}/interior{mx:.*}/{year}")
    @Produces("text/plain")
    public String getPathInTheMiddle(@PathParam("firm") String firm,
                                     @PathParam("model") PathSegment model,
                                     @PathParam("mx") PathSegment mx,
                                     @PathParam("year") int year) {
        final String name = model.getPath();
        final MultivaluedMap<String, String> modelParams = model.getMatrixParameters();
        final MultivaluedMap<String, String> interiorParams = mx.getMatrixParameters();
        String colorM = modelParams.get("color").get(0);
        String engine = modelParams.get("engine").get(0);
        String colorI = interiorParams.get("color").get(0);
        return "firm[" + firm
                + "] model[" + name
                + "] engine[" + engine
                + "] color[" + colorM + "," + colorI
                + "] year[" + year + "]";
    }
}
