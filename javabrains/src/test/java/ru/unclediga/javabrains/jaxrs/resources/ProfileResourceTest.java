package ru.unclediga.javabrains.jaxrs.resources;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.*;

public class ProfileResourceTest {
    private static final URI baseUri = UriBuilder.fromUri("http://localhost").port(7778).build();
    private static final ResourceConfig config = new ResourceConfig(ProfileResource.class);
    private static final Server server = JettyHttpContainerFactory.createServer(baseUri, config);
    private static final Client client = ClientBuilder.newClient();
    private static final WebTarget baseTarget = client.target(baseUri);

    @BeforeClass
    public static void setUp() throws Exception {
        server.setStopAtShutdown(true);
        server.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Before
    public void init() {
        DatabaseClass.initProfiles();
    }

    @Test
    public void profilesGetXTest() {
        final Response response = baseTarget
                .path("/profiles")
                .request()
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertTrue(entity.contains("<id>1</id><profileName>a1</profileName>"));
        assertTrue(entity.contains("<id>2</id><profileName>a2</profileName>"));
    }

    @Test
    public void profileGetXTest() {
        final Response response = baseTarget
                .path("/profiles/a1")
                .request()
                .get();
        String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertTrue(entity.contains("<id>1</id><profileName>a1</profileName>"));
        assertFalse(entity.contains("<id>2</id><profileName>a2</profileName>"));
    }
}
