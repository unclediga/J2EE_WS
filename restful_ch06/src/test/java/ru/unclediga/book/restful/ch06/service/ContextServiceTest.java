package ru.unclediga.book.restful.ch06.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;

import java.io.IOException;
import java.net.InetSocketAddress;

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
}
