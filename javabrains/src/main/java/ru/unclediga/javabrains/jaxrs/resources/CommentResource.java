package ru.unclediga.javabrains.jaxrs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
/*******************************************************************

 NOTE: The class level @Path annotation is optional for sub resources!

 *******************************************************************/
public class CommentResource {
    @GET
    public String getComments() {
        return "Hello from Subresource";
    }
}
