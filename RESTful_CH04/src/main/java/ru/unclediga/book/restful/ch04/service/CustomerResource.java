package ru.unclediga.book.restful.ch04.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.unclediga.book.restful.ch04.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerResource {
    protected static final AtomicInteger idCnt = new AtomicInteger();
    protected static final Map<Integer, Customer> db = new ConcurrentHashMap<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEcho() {
        System.out.println("EUROPE db size = " + db.size());
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCustomer(InputStream is) {
        Customer customer = readCustomer(is);
        customer.setId(idCnt.incrementAndGet());
        db.put(idCnt.get(), customer);
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public StreamingOutput getCustomer(@PathParam("id") Integer id) {
        Customer customer = db.get(id);
        System.out.println("db size:" + db.size());
        System.out.println("id : " + id);
        System.out.println("cust : " + customer);

        if (customer == null) {
            throw new WebApplicationException("Not found item id=[" + id + "]");
        }


        return outputStream -> {
            System.out.println("customer = " + customer);
            writeCustomer(outputStream, customer);
        };
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCustomer(@PathParam("id") int id, InputStream is) {
        Customer customer = db.get(id);
        Customer newCustomer = readCustomer(is);
        if (customer != null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
    }

    protected Customer readCustomer(InputStream is) {
        Customer customer = new Customer();
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            if (root.getAttribute("id") != null && !root.getAttribute("id").trim().equals(""))
                customer.setId(Integer.valueOf(root.getAttribute("id")));
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element element = (Element) nodes.item(i);
                if (element.getTagName().equals("first-name")) {
                    customer.setFirstName(element.getTextContent());
                } else if (element.getTagName().equals("last-name")) {
                    customer.setLastName(element.getTextContent());
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
        }
        return customer;
    }

    protected void writeCustomer(OutputStream os, Customer customer) {
        PrintWriter writer = new PrintWriter(os);
        writer.print("<customer id=\"" + customer.getId() + "\">");
        writer.print("<first-name>" + customer.getFirstName() + "</first-name>");
        writer.print("<last-name>" + customer.getLastName() + "</last-name>");
        writer.print("</customer>");
        writer.flush();
    }
}
