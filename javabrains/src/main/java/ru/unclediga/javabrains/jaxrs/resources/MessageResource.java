package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
}
