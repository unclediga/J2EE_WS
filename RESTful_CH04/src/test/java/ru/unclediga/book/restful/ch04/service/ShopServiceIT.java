package ru.unclediga.book.restful.ch04.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.book.restful.ch04.domain.Customer;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.*;

public class ShopServiceIT {

    private final static URI uri = UriBuilder.fromUri("http://localhost").port(7778).build();
    private static String customerXML;
    private static Customer PETER_PAN = new Customer(1,"Peter","Pan");


    private static HttpServer server;

    @BeforeClass
    public static void init() throws Exception {
        System.out.printf("scheme[%s] host[%s] port[%s] path[%s]", uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());
        // http://localhost:8282

        server = HttpServer.create(new InetSocketAddress(uri.getHost(), uri.getPort()), 0);
        // server = HttpServer.create(new InetSocketAddress("localhost", 8282), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ShoppingApplication(), HttpHandler.class);
        server.createContext("/services", handler);
        server.start();

        System.out.printf("scheme[%s] host[%s] port[%s] path[%s]", uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath());

        CustomerResource.db.put(CustomerResource.idCnt.incrementAndGet(), PETER_PAN);
        FirstLastCustomerResource.db.put(PETER_PAN.getFirstName() + "-" + PETER_PAN.getLastName(), PETER_PAN);

        customerXML =
                "<customer id=\"1\">" +
                "<first-name>Peter</first-name>" +
                "<last-name>Pan</last-name>" +
                "</customer>";


    }

    @AfterClass
    public static void stop() {
        server.stop(0);
    }

    @Test
    public void t0() {
        InputStream is = new ByteArrayInputStream(customerXML.getBytes());
        Customer customer = new CustomerResource().readCustomer(is);
        assertNotNull(customer);
        assertEquals(PETER_PAN.getFirstName(), customer.getFirstName());
        assertEquals(PETER_PAN.getLastName(), customer.getLastName());

        is = new ByteArrayInputStream(customerXML.getBytes());
        customer = new FirstLastCustomerResource().readCustomer(is);
        assertNotNull(customer);
        assertEquals(PETER_PAN.getFirstName(), customer.getFirstName());
        assertEquals(PETER_PAN.getLastName(), customer.getLastName());
    }

    @Test
    public void t00() throws IOException {

        OutputStream os = new ByteArrayOutputStream();
        new CustomerResource().writeCustomer(os, PETER_PAN);
        os.close();
        assertEquals(customerXML,os.toString());
    }

    @Test
    public void t1_POST() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/customers/europe-db");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        Response response = builder.post(Entity.xml(customerXML));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertTrue(CustomerResource.db.size() == 2);
    }

    @Test
    public void t0_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/echo");
        Invocation.Builder builder = target.request();
        Response response = builder.get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void t1_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/customers/echo2");
        Invocation.Builder builder = target.request();
        Response response = builder.get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void t2_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/customers/europe-db/1");

        final Response response = target.request(MediaType.TEXT_PLAIN).get();
        String customer = response.readEntity(String.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        System.out.println("ST " + response.getStatus());
        System.out.println("STINFO " + response.getStatusInfo());
        System.out.println("CUST " + customer);
        assertEquals(customer,customerXML);
    }

    @Test
    public void t4_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/customers/europe-db");
        Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);
        Response response = builder.get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void t5_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:7778/services/customers/russia-db");
        Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);
        Response response = builder.get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}