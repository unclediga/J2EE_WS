package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}
