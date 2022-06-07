package ru.unclediga.book.restful.ch05.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class InjectionIT {

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
    public void testIndexPage() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/index.jsp")
                .request()
                .get();
        /*
             !!!!! NOT_FOUND !!!!!
         */
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEcho() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/cars/echo")
                .request("text/plain")
                .get();
        System.out.println("RESP = " + response.getStatusInfo());
        assertEquals("send ECHO from @Path(/echo)", response.readEntity(String.class));
    }

    @Test
    public void testEcho2() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/services/cars/echo")
                .request("text/plain")
                .get();
        /*
             !!!!! NOT_FOUND !!!!!
         */
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testManyParam() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/cars/p123-assa")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("par1[123] par2[assa]", response.readEntity(String.class));
    }

    @Test
    public void testPathRegExp() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/cars/firmhyundai/a/b/c/year2010")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("p1[hyundai] many[a/b/c] p2[2010]",
                response.readEntity(String.class));
    }

    @Test
    public void testPathInTheMiddle() {
        final Client client = ClientBuilder.newClient();



        /*
         * CHOICE endpoints:
         *   @Path("/{firm}/{model}/interior{mx:.*}/{year}")   - more specific ("../interior/..")
         *   @Path("/firm{p1}/{many:.*}/year{p2}")
         * */
        Response response = client
                .target("http://localhost:7778/cars/firmhyundai/getz/interior/year2010")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());


        /*
         * CHOICE endpoints:
         *   @Path("/{firm}/{model}/interior{mx:.*}/{year}")
         *   @Path("/firm{p1}/{many:.*}/year{p2}")              - more specific (".../year")
         * */
        response = client
                .target("http://localhost:7778/cars/firmhyundai/getz/inter/year2010")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("p1[hyundai] many[getz/inter] p2[2010]",
                response.readEntity(String.class));


        response = client
                .target("http://localhost:7778/cars/firmhyundai/year2010")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("p1[hyundai] p2[2010]",
                response.readEntity(String.class));

        /* NO endpoints with int p2: 5 digits  */
        response = client
                .target("http://localhost:7778/cars/firmhyundai/year20109")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    @Test
    public void testMatrix() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/cars/hyundai/getz;color=blue;engine=1_4/interior;color=black/2010")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("firm[hyundai] model[getz] engine[1_4] color[blue,black] year[2010]",
                response.readEntity(String.class));
    }

    @Test
    public void testQuery() {
        final Client client = ClientBuilder.newClient();
        final Response response = client
                .target("http://localhost:7778/cars/hyundai/getz?start=1&size=3")
                .request("text/plain")
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("query start[1] size[3]",
                response.readEntity(String.class));
    }

    @Test
    public void testUriInfo() {
        Response response = null;
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target("http://localhost:7778/cars");
        response = target
                .queryParam("start", 2)
                .queryParam("size", 3)
                .request("text/plain")
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("uriInfo start[2] size[3] path[cars]",
                response.readEntity(String.class));

        response = target
                .path("hyundai")
                .queryParam("start", 4)
                .queryParam("size", 5)
                .request("text/plain")
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("uriInfo start[4] size[5] path[cars/hyundai]",
                response.readEntity(String.class));
    }
}
