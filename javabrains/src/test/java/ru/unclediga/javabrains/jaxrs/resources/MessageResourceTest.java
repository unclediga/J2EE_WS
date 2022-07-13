package ru.unclediga.javabrains.jaxrs.resources;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.MyResource;
import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageResourceTest {
    public static final String BASE_URI = "http://localhost:7778/";
    public static Server server = null;

    private static final URI baseUri = UriBuilder.fromUri("http://localhost").port(7778).build();
    private static final Map<Long, Message> messages = DatabaseClass.getMessages();
    private final Client client = ClientBuilder.newClient();
    private final WebTarget baseTarget = client.target(baseUri);


    public static Server startServer() {
        // scan packages
        // final ResourceConfig config = new ResourceConfig().packages("com.mkyong");

        final ResourceConfig config = new ResourceConfig(MyResource.class, MessageResource.class);

        return JettyHttpContainerFactory.createServer(baseUri, config);
    }


    @BeforeClass
    public static void init() {
        server = startServer();
        server.setStopAtShutdown(true);
    }

    @AfterClass
    public static void shutdown() throws Exception {
        server.stop();
    }

    @Test
    public void MyResourceTest() {
        final Response response = baseTarget
                .path("myresource")
                .request(MediaType.TEXT_PLAIN)
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Got it!", entity);
    }

    @Test
    public void MessagesGetXTest() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages")
                .request(MediaType.APPLICATION_XML)
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(entity.contains("<id>1</id>"));
        assertTrue(entity.contains("<id>2</id>"));
    }

    @Test
    public void MessagesGetX1Test() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages")
                .path("/1")
                .request(MediaType.APPLICATION_XML)
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(entity.contains("<id>1</id>"));
        assertFalse(entity.contains("<id>2</id>"));
    }

    @Test
    public void MessagesGetJTest() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages/json")
                .request(MediaType.APPLICATION_JSON)
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(entity.contains("\"id\":1"));
        assertTrue(entity.contains("\"id\":2"));
    }

    @Test
    public void MessagesPOSTJTest() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages/json")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json("{\"message\":\"Hello from test!\"}"));
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(3, messages.size());
        assertTrue(messages.containsKey(3L));
        assertEquals("Hello from test!", messages.get(3L).getMessage());


    }

    @Test
    public void MessagesPUTJ1Test() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages/json")
                .path("/1")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json("{\"id\":1,\"message\":\"Hello one more time!\"}"));
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertTrue(messages.containsKey(1L));
        assertEquals("Hello one more time!", messages.get(1L).getMessage());
    }

    @Test
    public void MessagesDELETEJ1Test() {
        DatabaseClass.initMessages();
        final Response response = client.target(baseUri)
                .path("/messages/json")
                .path("/1")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertFalse(messages.containsKey(1L));
        assertTrue(messages.containsKey(2L));
    }
}
