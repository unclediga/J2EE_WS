package ru.unclediga.javabrains.jaxrs.resources;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

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
    public void getComments() {
        final Response response = client
                .target(baseURI)
                .path("1/comments")
                .request().get();
        final String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Hello from Subresource", entity);
    }
}