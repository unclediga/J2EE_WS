package ru.unclediga.javabrains.jaxrs;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

import static org.junit.Assert.*;

public class MyEmbeddedServerTest {
    public static Server server = null;
    private static final URI baseUri = UriBuilder.fromUri("http://localhost").port(7779).build();
    private final Client client = ClientBuilder.newClient();
    private final WebTarget baseTarget = client.target(baseUri);


    public static Server startServer() {
        // scan packages
        // final ResourceConfig config = new ResourceConfig().packages("com.mkyong");

        ResourceConfig config = new ResourceConfig(MyResource.class);
        config = config.register(RegisteredFilter.class);
        config = config.register(PoweredByFilter.class);
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
        Response response;
        response = baseTarget
                .path("myresource")
                .request(MediaType.TEXT_PLAIN)
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("My JAX-RS", response.getHeaderString("X-Powered-By"));
        assertNotNull(response.getHeaderString(RegisteredFilter.CONTAINER_HEADER_NAME));
        assertNull(response.getHeaderString(RegisteredFilter.CLIENT_HEADER_NAME));

        response = baseTarget
                .path("myresource")
                .register(RegisteredFilter.class)
                .request(MediaType.TEXT_PLAIN)
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("My JAX-RS", response.getHeaderString("X-Powered-By"));
        assertNotNull(response.getHeaderString(RegisteredFilter.CONTAINER_HEADER_NAME));
        assertNotNull(response.getHeaderString(RegisteredFilter.CLIENT_HEADER_NAME));
    }
}
