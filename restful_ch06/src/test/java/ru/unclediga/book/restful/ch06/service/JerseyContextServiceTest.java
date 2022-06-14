package ru.unclediga.book.restful.ch06.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;

public class JerseyContextServiceTest extends JerseyTest {
    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
//        return new GrizzlyTestContainerFactory();
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
//        return ServletDeploymentContext
//                .builder(new ResourceConfig(ContextService.class))
//                        .build();
//        return ServletDeploymentContext
//                .builder(new ResourceConfig(ContextService.class))
//                .servletPath("/").contextPath("/")
//                        .build();


        final ServletContainer container = new ServletContainer(new ResourceConfig(ContextService.class));
        final ServletDeploymentContext context = ServletDeploymentContext
                .forServlet(container)
                .contextPath("/jersey/test")
                .build();

        return context;
    }

    @Test
    public void testEchoService() {
        Response response;
        response = target("/fs/echo").request(MediaType.TEXT_PLAIN).get();
        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("send ECHO from /fs/echo", response.readEntity(String.class));

    }

    @Test
    public void testInfo() {
        final Response response = target("fs/info").request(MediaType.TEXT_PLAIN).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        System.out.println(response.readEntity(String.class));
    }

    @Test
    public void testFileService() {

        final Response response = target("fs/file").queryParam("file_name", "content.txt")
                .request(MediaType.TEXT_PLAIN).get();

        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        System.out.println(response.readEntity(String.class));
        assertEquals("1. Hello\n" +
                "2. World\n" +
                "3. !!!", response.readEntity(String.class));
    }

}
