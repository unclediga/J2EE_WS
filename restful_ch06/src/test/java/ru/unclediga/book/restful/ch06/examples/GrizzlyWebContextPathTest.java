package ru.unclediga.book.restful.ch06.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test support for context path in {@link org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory}.
 *
 * @author Paul Sandoz
 * @author Marek Potociar
 *
 * https://github.com/eclipse-ee4j/jersey/blob/master/test-framework/providers/grizzly2/src/test/java/org/glassfish/jersey/test/grizzly/web/context/GrizzlyWebContextPathTest.java
 */
public class GrizzlyWebContextPathTest extends JerseyTest {

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Path("contextroot")
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
        return ServletDeploymentContext.forPackages(this.getClass().getPackage().getName())
                .contextPath("context")
                .build();
    }

    @Test
    public void testGet() {
        WebTarget target = target("contextroot");

        String s = target.request().get(String.class);
        assertEquals("GET", s);

        assertEquals("/context/contextroot", target.getUri().getRawPath());
    }

    @Test
    public void testGetSub() {
        WebTarget target = target("contextroot/sub");

        String s = target.request().get(String.class);
        assertEquals("sub", s);

        assertEquals("/context/contextroot/sub", target.getUri().getRawPath());
    }
}