package ru.unclediga.book.restful.ch05.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
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

    /*
    @Test
    public void testCarResource() throws Exception
    {
        DefaultHttpClient client = new DefaultHttpClient();

        System.out.println("**** CarResource Via @MatrixParam ***");
        HttpGet get = new HttpGet("http://localhost:9095/cars/matrix/mercedes/e55;color=black/2006");
        HttpResponse response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        String line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println("**** CarResource Via PathSegment ***");
        get = new HttpGet("http://localhost:9095/cars/segment/mercedes/e55;color=black/2006");
        response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println("**** CarResource Via PathSegments ***");
        get = new HttpGet("http://localhost:9095/cars/segments/mercedes/e55/amg/year/2006");
        response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println("**** CarResource Via PathSegment ***");
        get = new HttpGet("http://localhost:9095/cars/uriinfo/mercedes/e55;color=black/2006");
        response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }
        System.out.println();
        System.out.println();

    }

    @Test
    public void testCustomerResource() throws Exception
    {
        DefaultHttpClient client = new DefaultHttpClient();

        System.out.println("**** CustomerResource No Query params ***");
        HttpGet get = new HttpGet("http://localhost:9095/customers");
        HttpResponse response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        String line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println("**** CustomerResource With Query params ***");
        get = new HttpGet("http://localhost:9095/customers?start=1&size=3");
        response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }

        System.out.println("**** CustomerResource With UriInfo and Query params ***");
        get = new HttpGet("http://localhost:9095/customers/uriinfo?start=2&size=2");
        response = client.execute(get);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        reader = new BufferedReader(new
                InputStreamReader(response.getEntity().getContent()));

        line = reader.readLine();
        while (line != null)
        {
            System.out.println(line);
            line = reader.readLine();
        }
    }
*/
}
