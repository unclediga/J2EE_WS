package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Comment;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/*******************************************************************

 NOTE: The class level @Path annotation is optional for sub resources!
 *******************************************************************/
public class CommentResource {
    private long messageId;
    private final MessageService service = new MessageService();

    public CommentResource(long messageId) {
        this.messageId = messageId;
    }

    @GET
    public List<Comment> getComments() {
        return DatabaseClass.getMessages().get(messageId).getAllComments();
    }

    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("commentId") int id) {
        return DatabaseClass.getMessages().get(messageId).getComments().get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response addComment(Comment comment, @Context UriInfo info) {
        service.addComment(messageId, comment);
        final URI location = info.getAbsolutePathBuilder().path(String.valueOf(comment.getId())).build();
        return Response
                .created(location)
                .build();
    }

    @DELETE
    @Path("/{commentId}")
    public void removeComment(@PathParam("commentId") int id) {
        service.removeComment(messageId, id);
    }
}
