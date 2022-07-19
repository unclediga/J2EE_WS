package ru.unclediga.javabrains.jaxrs.resources;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Comment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentResourceTest {

    private static final URI baseURI = UriBuilder.fromUri("http://localhost").port(7778).path("messages").build();
    private static final Server server = JettyHttpContainerFactory.createServer(baseURI,
            new ResourceConfig(MessageResource.class, CommentResource.class));
    private final Client client = ClientBuilder.newClient();

    @BeforeClass
    public static void init() throws Exception {
        server.setStopAtShutdown(true);
        server.start();
    }

    @AfterClass
    public static void shutdown() throws Exception {
        server.stop();
    }

    @Test
    public void getAllComments() {
        final Response response = client
                .target(baseURI)
                .path("/1")
                .path("/comments")
                .request(MediaType.APPLICATION_XML)
                .get();
        final String entity = response.readEntity(String.class);
        System.out.println(entity);
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void addComment() {
        final Response response = client
                .target(baseURI)
                .path("/1/comments")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.xml(
                        "<comment><id>4</id><text>Comment 1-4</text></comment>")
                );
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(UriBuilder.fromUri(baseURI).path("/1/comments/4").build(), response.getLocation());
        assertEquals(4, DatabaseClass.getMessages().get(1L).getAllComments().size());
        System.out.println(DatabaseClass.getMessages().get(1L).getAllComments());
    }

    @Test
    public void removeComment() {
        final Response response = client
                .target(baseURI)
                .path("/1/comments/2")
                .request()
                .delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals(2, DatabaseClass.getMessages().get(1L).getAllComments().size());
    }
}