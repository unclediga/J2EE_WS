package ru.unclediga.book.restful.ch03.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.xml.bind.StringInputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.book.restful.ch03.domain.Customer;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShopServiceIT {

    private final static URI uri = UriBuilder.fromUri("http://localhost").port(8282).build();
    private static String customerXML;

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

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Peter");
        customer.setLastName("Pen");
        CustomerService.customerDB.put(1, customer);

        customerXML = "<customer id=\"1\"> " +
                "<first-name>Peter</first-name>" +
                "<last-name>Pen</last-name>" +
                "</customer>";
    }

    @AfterClass
    public static void stop() {
        server.stop(0);
    }

    @Test
    public void t0() {
        InputStream is = new ByteArrayInputStream(customerXML.getBytes());
        Customer customer = new CustomerService().readCustomer(is);
        assertNotNull(customer);
        assertEquals("Peter", customer.getFirstName());
        assertEquals("Pen", customer.getLastName());
    }

    @Test
    public void t1_POST() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        Response response = builder.post(Entity.xml(customerXML));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void t2_GET() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers/1");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
//        Response response = builder.get();
//        String customer = response.readEntity(String.class);
        String customer = builder.get(String.class);
        assertNotNull(customer);
        System.out.println("GET CUSTOMER => " + customer);
    }
}