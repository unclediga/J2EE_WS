package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/secured")
public class MySecured {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAuthMessage(){
        return "Hello from Authority";
    }
}
