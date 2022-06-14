package ru.unclediga.book.restful.ch06.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class ContextServiceTest {
    private static HttpServer server;
    private static WebTarget rootTarget;

    @BeforeClass
    public static void init() throws IOException {

        final HttpHandler httpHandler = RuntimeDelegate.getInstance()
                .createEndpoint(new CustomerApplication(), HttpHandler.class);
        server = HttpServer.create(new InetSocketAddress("localhost", 7778), 0);
        server.createContext("/", httpHandler);
        server.start();

        rootTarget = ClientBuilder.newClient().target("http://localhost:7778");

    }

    @AfterClass
    public static void shutdown() {
        server.stop(0);
    }


    @Test
    public void testFileServiceEcho() {
        WebTarget target = rootTarget.path("/fs/echo");
        final Response response = target.request(MediaType.TEXT_PLAIN).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("send ECHO from /fs/echo", response.readEntity(String.class));
    }

    @Test
    public void testInfo() {
        WebTarget target = rootTarget.path("/fs/info");
        final Response response = target.request(MediaType.TEXT_PLAIN).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        System.out.println(response.readEntity(String.class));
    }

    @Test
    public void testFileService() {
        WebTarget target = rootTarget.path("/fs/file");
        final Response response = target.queryParam("file_name", "content.txt")
                .request(MediaType.TEXT_PLAIN).get();

        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());
//        System.out.println(response.readEntity(String.class));
    }

    @Test
    public void testFileService2() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(7779).build();
        ResourceConfig config = new ResourceConfig(ContextService.class);
        HttpServer server2 = JdkHttpServerFactory.createHttpServer(baseUri, ResourceConfig.forApplication(new CustomerApplication()));
//        HttpServer server2 = JdkHttpServerFactory.createHttpServer(baseUri, config);
        final HttpHandler httpHandler = RuntimeDelegate.getInstance()
                .createEndpoint(new CustomerApplication(), HttpHandler.class);

//        server2.createContext("/", httpHandler);
        server2.createContext("/");
        //System.out.println(server2.


        WebTarget rootTarget2 = ClientBuilder.newClient().target("http://localhost:7779");
        WebTarget target;
        target = rootTarget2.path("/fs/echo");
        Response response;
        response = target.request(MediaType.TEXT_PLAIN).get();
        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());

        target = rootTarget2.path("/fs/info");
        response = target.request(MediaType.TEXT_PLAIN).get();
        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());
        System.out.println(response.readEntity(String.class));


        target = rootTarget2.path("/fs/file");
        response = target.queryParam("file_name", "content.txt")
                .request(MediaType.TEXT_PLAIN).get();

        System.out.println("Status " + response.getStatus());
        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());
        server2.stop(0);

    }

//    @Test
//    public void testFileService3() throws IOException {
//       // URI baseUri = UriBuilder.fromUri("http://localhost/").port(7779).build();
//
//
//        final HttpHandler httpHandler = RuntimeDelegate.getInstance()
//                .createEndpoint(new CustomerApplication(), HttpHandler.class);
//        server = HttpServer.create(new InetSocketAddress("localhost", 7779), 0);
//        server.createContext("/", httpHandler);
//        server.start()
//
//
////        HttpServer httpServer = GrizzlyServerFactory.createHttpServer(BASE_URI, new HttpHandler() {
////
////            @Override
////            public void service(Request rqst, Response rspns) throws Exception {
////                rspns.setStatus(404, "Not found");
////                rspns.getWriter().write("404: not found");
////            }
////        });
////
//        final String JERSEY_SERVLET_CONTEXT_PATH = "";
//
//        // Initialize and register Jersey Servlet
//        WebappContext context = new WebappContext("WebappContext", JERSEY_SERVLET_CONTEXT_PATH);
//        ServletRegistration registration = context.addServlet("ServletContainer", ServletContainer.class);
//        registration.setInitParameter(ServletContainer.RESOURCE_CONFIG_CLASS,
//                ClassNamesResourceConfig.class.getName());
//        registration.setInitParameter(ClassNamesResourceConfig.PROPERTY_CLASSNAMES, LolCat.class.getName());
//        registration.addMapping("/*");
//        context.deploy(server);
//
//        System.in.read();
//        httpServer.stop();
//
//
//
//
//
//
//
//
//        ResourceConfig config = new ResourceConfig(ContextService.class);
//        HttpServer server2 = JdkHttpServerFactory.createHttpServer(baseUri, config);
//        //server2.start();
//
//        WebTarget rootTarget2 = ClientBuilder.newClient().target("http://localhost:7779");
//        WebTarget target = rootTarget2.path("/fs/file");
//
//        final Response response = target.queryParam("file_name", "content.txt")
//                .request(MediaType.TEXT_PLAIN).get();
//
//        System.out.println("Status " + response.getStatus());
//        System.out.println("Status " + response.getStatusInfo().getReasonPhrase());
////        System.out.println(response.readEntity(String.class));
//        server2.stop(0);
//
//    }
}
