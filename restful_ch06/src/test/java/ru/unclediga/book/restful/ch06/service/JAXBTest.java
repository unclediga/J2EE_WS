package ru.unclediga.book.restful.ch06.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import ru.unclediga.book.restful.ch06.domain.Customer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;

public class JAXBTest {

    private final Customer PETER_PEN = Customer.getPeterPan();
    private final Customer WENDY_DARLING = Customer.getWendyDarling();

    private static HttpServer server;
    private static WebTarget rootTarget;

    @BeforeClass
    public static void init() throws IOException {

        final HttpHandler httpHandler = RuntimeDelegate.getInstance()
                .createEndpoint(new CustomerApplication(), HttpHandler.class);
        server = HttpServer.create(new InetSocketAddress("localhost", 7778),0);
        server.createContext("/", httpHandler);
        server.start();

        rootTarget = ClientBuilder.newClient().target("http://localhost:7778");

    }

    @AfterClass
    public static void shutdown(){
        server.stop(0);
    }


    @Test
    public void testJAXB() throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(Customer.class);
        StringWriter writer = new StringWriter();
        context.createMarshaller().marshal(PETER_PEN, writer);
        assertTrue(writer.toString().contains(PETER_PEN.toXML()));

        final StringReader reader = new StringReader(writer.toString());
        final Customer p = (Customer) context.createUnmarshaller().unmarshal(reader);
        assertEquals(PETER_PEN, p);
    }

    @Test
    public void testGetCustomer(){
        Response response;

        response = rootTarget
                .path("/customers/1")
                .request(MediaType.APPLICATION_XML_TYPE)
                .get();

        Customer pp = response.readEntity(Customer.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(PETER_PEN, pp);

        response = rootTarget
                .path("/customers")
                .path("2")
                .request(MediaType.APPLICATION_XML_TYPE)
                .get();

        Customer wd = response.readEntity(Customer.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(WENDY_DARLING, wd);
    }

    @Test
    public void testPostCustomer(){

        Customer customer = Customer.getPeterPan();
        customer.setId(0);

        Response response;
        response = rootTarget
                .path("/customers/not_rest")
                .request(MediaType.APPLICATION_XML_TYPE)
                .post(Entity.xml(customer));

        Customer pp = response.readEntity(Customer.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(PETER_PEN, pp);

        response = rootTarget
                .path("/customers")
                .request(MediaType.WILDCARD)
                .post(Entity.xml(customer));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(0, response.getLength());
        assertEquals(URI.create(rootTarget.getUri() + "/customers/" + pp.getId()), response.getLocation());
    }

    @Test
    public void testPutCustomer() {

        Customer customer;
        Response response;
        WebTarget target = rootTarget.path("/customers");



        customer = Customer.getPeterPan();
        response = target
                .path("1")
                .request()
                .put(Entity.xml(customer));

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        customer = Customer.getWendyDarling();
        response = target
                .path("2")
                .request()
                .put(Entity.xml(customer));

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
