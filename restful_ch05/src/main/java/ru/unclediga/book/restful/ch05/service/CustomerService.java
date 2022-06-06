package ru.unclediga.book.restful.ch05.service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GET
    @Path("/header")
    @Produces("text/plain")
    public String getHeader(@HeaderParam("Referer") String referer,
                            @HeaderParam("Content-Language") String contentLang) {
        return "Referer[" + referer + "] Content-Language[" + contentLang + "]";
    }

    @GET
    @Path("/headers")
    @Produces("text/plain")
    public String getHeaders(@Context HttpHeaders headers) {
        final MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        List<Object> l = new ArrayList<>(3);
        for(String key : requestHeaders.keySet()){
            switch (key) {
                case ("Referer"):
                    l.set(0, requestHeaders.get("Referer"));
                    break;
                case ("Content-Language"):
                    l.set(1, requestHeaders.get("Content-Language"));
                    break;
                case ("MyHeader"):
                    l.set(2, requestHeaders.get("MyHeader"));
                    break;
            }
        }
        return l.toString();
    }
}
