package ru.unclediga.book.restful.ch05.service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("/customers")
public class CustomerService {

    @GET
    @Path("/echo")
    @Produces("text/plain")
    public String getEcho() {
        return "send ECHO from @Path(/echo)";
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/plain")
    public String getFormsParams(@FormParam("firstname") String p1,
                                 @FormParam("lastname") String p2) {
        if (p1 == null || p2 == null) {
            throw new WebApplicationException("not enough params");
        }
        return "firstname[" + p1 + "] lastname[" + p2 + "]";
    }
}
