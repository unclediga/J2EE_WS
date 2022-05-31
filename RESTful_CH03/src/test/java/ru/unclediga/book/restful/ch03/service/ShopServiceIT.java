package ru.unclediga.book.restful.ch03.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.book.restful.ch03.domain.Customer;

import javax.ws.rs.PathParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.junit.Assert.*;

public class ShopServiceIT {

    private final static URI uri = UriBuilder.fromUri("http://localhost").port(8282).build();
    private static String customerXML;
    private static final Customer PETER_PAN = new Customer(1, "Peter", "Pan", "Peace st");
    private static final Customer WENDY_DARLING = new Customer(2, "Wendy", "Darling", "Kensington Gardens");

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


        customerXML = "<customer id=\"1\">" +
                "<first-name>" + PETER_PAN.getFirstName() + "</first-name>" +
                "<last-name>" + PETER_PAN.getLastName() + "</last-name>" +
                "<street>" + PETER_PAN.getStreet() + "</street>" +
                "</customer>";
    }

    @AfterClass
    public static void stop() {
        server.stop(0);
    }

    @Before
    public void initDb() {
        CustomerService.customerDB.clear();
    }

    @Test
    public void t0() {
        InputStream is = new ByteArrayInputStream(customerXML.getBytes());
        Customer customer = new CustomerService().readCustomer(is);
        assertNotNull(customer);
        assertEquals(PETER_PAN.getFirstName(), customer.getFirstName());
        assertEquals(PETER_PAN.getLastName(), customer.getLastName());
        assertEquals(PETER_PAN.getStreet(), customer.getStreet());
    }

    @Test
    public void t1_POST() {

        assertTrue(CustomerService.customerDB.isEmpty());

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        Response response = builder.post(Entity.xml(customerXML));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        assertEquals(1, CustomerService.customerDB.size());
        final Customer customer = CustomerService.customerDB.get(1);
        assertEquals(PETER_PAN.getLastName(), customer.getLastName());
        assertEquals(PETER_PAN.getFirstName(), customer.getFirstName());
        assertEquals(PETER_PAN.getStreet(), customer.getStreet());
    }

    @Test
    public void t2_GET() {

        CustomerService.customerDB.put(1, PETER_PAN);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers/1");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        String customer = builder.get(String.class);
        assertNotNull(customer);
        assertEquals(customerXML, customer);
        System.out.println("GET CUSTOMER => " + customer);
    }

    @Test
    public void t3_PUT() {

        CustomerService.customerDB.put(1, PETER_PAN.copy());

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers/1");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        String newCustomerXML = customerXML.replace("Peace st", "Lenin st");
        Response response = builder.put(Entity.xml(newCustomerXML));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus()); /* 204 */

        assertEquals(1, CustomerService.customerDB.size());
        final Customer customer = CustomerService.customerDB.get(1);
        assertEquals(PETER_PAN.getLastName(), customer.getLastName());
        assertEquals(PETER_PAN.getFirstName(), customer.getFirstName());
        assertEquals( "Lenin st", customer.getStreet());
    }

    @Test
    public void t4_DEL() {

        CustomerService.customerDB.put(PETER_PAN.getId(), PETER_PAN.copy());
        CustomerService.customerDB.put(WENDY_DARLING.getId(), WENDY_DARLING.copy());

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8282/services/customers/1");
        Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
        Response response = builder.delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus()); /* 204 */

        assertEquals(1, CustomerService.customerDB.size());
        final Customer customer = CustomerService.customerDB.get(2);
        assertEquals(WENDY_DARLING.getLastName(), customer.getLastName());
        assertEquals(WENDY_DARLING.getFirstName(), customer.getFirstName());
        assertEquals(WENDY_DARLING.getStreet(), customer.getStreet());
    }

}