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

public class InjectDemoResourceTest {

    private static final URI baseUri = UriBuilder
            .fromUri("http://localhost")
            .port(7778)
            .path("/injectdemo")
            .build();
    private static Server server;
    private final Client client = ClientBuilder.newClient();


    @BeforeClass
    public static void init() throws Exception {
        final ResourceConfig config = new ResourceConfig(InjectDemoResource.class);
        server = JettyHttpContainerFactory.createServer(baseUri, config);
        server.setStopAtShutdown(true);
        server.start();
    }

    @AfterClass
    public static void shutdown() throws Exception {
        server.stop();
    }

    @Test
    public void getParamsUsingAnnotations() {
        final Response response = client.target(baseUri)
                .path("/annotations")
                .matrixParam("mxParam1", "mx1")
                .matrixParam("mxParam2", "mx2-1", "mx2-2")
                .request()
                .header("mySessionId", 123)
                .cookie("myName", "sweet cookie")
                .get();

        final String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("[matrix param: mx1]\n" +
                        "[matrix params: [mx2-1, mx2-2]]\n" +
                        "[header param: 123]\n" +
                        "[cookie: sweet cookie]"
                , entity);

    }

    @Test
    public void getParamsUsingContext() {
        final Response response = client.target(baseUri)
                .path("/context")
                .request()
                .cookie("name", "abc")
                .get();

        final String entity = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        System.out.println(entity);

        assertEquals("Path:http://localhost:7778/injectdemo/context " +
                "cookie:{name=$Version=1;name=abc}", entity);
    }
}