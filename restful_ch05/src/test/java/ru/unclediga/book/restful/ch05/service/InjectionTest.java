package ru.unclediga.book.restful.ch05.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;

public class InjectionTest {

    private static HttpServer server;

    @BeforeClass
    public static void init() throws Exception {
        //server = HttpServer.create(new InetSocketAddress("localhost", 7778), 0);
        //final HttpHandler endpoint = RuntimeDelegate.getInstance().createEndpoint(null, HttpHandler.class);

    }

    @AfterClass
    public static void fin(){

    }

    @Test
    public void testManyPathParams() throws Exception{

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
