package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Message getMessage(@PathParam("messageId") long id){
        return service.getMessage(id);
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
    public Message addMessageJ(Message message){
        return service.addMessage(message);
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
}
