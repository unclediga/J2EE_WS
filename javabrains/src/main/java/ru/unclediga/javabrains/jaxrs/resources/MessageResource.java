package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.exception.DataNotFoundException;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/messages")
public class MessageResource {
    private final MessageService service = new MessageService();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getMessages(){
        return service.getAllMessages();
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> getMessagesForYear(@QueryParam("y") int year){
        if (year > 0){
            return service.getAllMessages(year);
        }
        return service.getAllMessages();
    }

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_XML)
    public Message getMessage(@PathParam("messageId") long id, @Context UriInfo info){

        final Message message = service.getMessage(id);
        if(message == null){
            throw new DataNotFoundException("Not found id["+ id +"]");
        }

        //final URI uri = info.getAbsolutePathBuilder().build() -> (!!!)full path at once
        final URI uri = info
                .getBaseUriBuilder()
                .path(MessageResource.class)
                .path(String.valueOf(id))
                .build();
        message.getLinks().clear();
        message.addLink(uri.toString(), "self");
        return message;
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getMessagesJ(){
        return service.getAllMessages();
    }

    @GET
    @Path("/json/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessageJ(@PathParam("messageId") long id){
        return service.getMessage(id);
    }

    @POST
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMessageJ(Message message, @Context UriInfo info){
        final Message newMessage = service.addMessage(message);
        final URI location = info.getAbsolutePathBuilder().path(String.valueOf(newMessage.getId())).build();
        return Response
                .created(location)
                .build();
    }

    @PUT
    @Path("/json/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message addMessageJ(Message message, @PathParam("messageId") long id){
        message.setId(id);
        return service.updateMessage(message);
    }

    @DELETE
    @Path("/json/{messageId}")
    public void addMessageJ(@PathParam("messageId") long id){
        service.removeMessage(id);
    }

    @Path("/{messageId}/comments")
    public CommentResource getComments(@PathParam("messageId") long id) {
        return new CommentResource(id);
    }
}

