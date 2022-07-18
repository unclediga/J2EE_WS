package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Comment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Map;

@Path("/")
/*******************************************************************

 NOTE: The class level @Path annotation is optional for sub resources!

 *******************************************************************/
public class CommentResource {
    private long messageId;

    public CommentResource(long messageId) {
        this.messageId = messageId;
    }

    @GET
    @Path("/")
    public Map<Long, Comment> getComments() {
        return DatabaseClass.getMessages().get(messageId).getComments();
    }
}
