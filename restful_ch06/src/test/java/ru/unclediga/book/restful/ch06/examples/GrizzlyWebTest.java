package ru.unclediga.book.restful.ch06.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic GrizzlyWebTestContainerFactory unit tests.
 *
 * @author Paul Sandoz
 * @author Marek Potociar
 */
public class GrizzlyWebTest extends JerseyTest {

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Path("root")
    public static class TestResource {
        @GET
        public String get() {
            return "GET";
        }

        @Path("sub")
        @GET
        public String getSub() {
            return "sub";
        }
    }


    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.forServlet(new ServletContainer(new ResourceConfig(TestResource.class))).build();
    }

    @Test
    public void testGet() {
        WebTarget target = target("root");

        String s = target.request().get(String.class);
        Assert.assertEquals("GET", s);
    }

    @Test
    public void testGetSub() {
        WebTarget target = target("root/sub");

        String s = target.request().get(String.class);
        Assert.assertEquals("sub", s);
    }
}