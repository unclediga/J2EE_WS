package ru.unclediga.book.restful.ch05.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class FormTest {
    private static HttpServer server;
    private final static URI uri = UriBuilder.fromUri("http://localhost").port(7778).build();

    @BeforeClass
    public static void init() throws Exception {


        System.out.printf("scheme[%s] host[%s] port[%s] path[%s]", uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
        // [http] [localhost] [7778] []

        server = HttpServer.create(new InetSocketAddress(uri.getHost(), uri.getPort()), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ShoppingApplication(), HttpHandler.class);
        server.createContext("/", handler);
        server.start();
    }

    @AfterClass
    public static void fin() {
        server.stop(0);
    }

    @Test
    public void testEcho() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/customers/echo")
                .request("text/plain")
                .get();
        System.out.println("RESP = " + response.getStatusInfo());
        assertEquals("send ECHO from @Path(/echo)", response.readEntity(String.class));
    }


    @Test
    public void testFormParams() {
        final Client client = ClientBuilder.newClient();
        final MultivaluedMap<String,String> formParams = new MultivaluedHashMap<>();
        formParams.add("firstname","AAA");
        formParams.add("lastname","BBB");
        final Response response = client
                .target("http://localhost:7778/customers")
                .request().post(Entity.form(formParams));
        System.out.println("RESP = " + response.getStatusInfo());
        assertEquals("firstname[AAA] lastname[BBB]", response.readEntity(String.class));
    }

    @Test
    public void testHeader() {
        final Client client = ClientBuilder.newClient();
        final MultivaluedMap<String,Object> headers = new MultivaluedHashMap<>();
        headers.add("Referer","localhost");
        headers.add("Content-Language","ru");
        headers.add("MyHeader","MyValue");
        final Response response = client
                .target("http://localhost:7778/customers/header")
                .request("text/plain")
                .headers(headers)
                .get();
        assertEquals("Referer[localhost] Content-Language[ru]", response.readEntity(String.class));
    }

    @Test
    public void testCookies() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target("http://localhost:7778/customers");
        Response response;
        response = target.path("cookies")
                .request("text/plain")
                .cookie("AAA", "cookieA")
                .cookie("BBB", "111")
                .cookie("CCC", "222")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        assertEquals("cookies:[cookieA, 111, 222]", response.readEntity(String.class));

        response = target.path("cookie")
                .request("text/plain")
                .cookie("AA", "cookieA")
                .cookie("BB", "111")
                .cookie("CC", "222")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("cookie:AA[cookieA] BB[111] CC[222]", response.readEntity(String.class));
    }

    @Test
    public void testBeanParam() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/customers/bean?last-name=Ivanov&AGE=35")
                .request("text/plain")
                .header("Content-Language", "ru")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("bean LastName[Ivanov] AGE[35] header[ru]",
                response.readEntity(String.class));
    }
}
