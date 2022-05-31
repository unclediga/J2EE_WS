package ru.unclediga.book.restful.ch03.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.unclediga.book.restful.ch03.domain.Customer;

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

@Path("/customers")
public class CustomerService implements CustomerResource {
    static Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    //    Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Response createCustomer(InputStream in) {
        Customer customer = readCustomer(in);
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        System.out.println("Create " + customer);
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }

    @Override
    public StreamingOutput getCustomer(int id) {
        Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws WebApplicationException {
                writeCustomer(outputStream, customer);
            }
        };
    }

    void writeCustomer(OutputStream outputStream, Customer customer) {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.printf("<customer id=\"%d\">" +
                        "<first-name>%s</first-name>" +
                        "<last-name>%s</last-name>" +
                        "<street>%s</street>" +
                        "</customer>",
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet());
        writer.flush();
        writer.close();
    }

    Customer readCustomer(InputStream is) {
        try {
            DocumentBuilder builder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            Customer customer = new Customer();
            if (root.getAttribute("id") != null
                    && !root.getAttribute("id").trim().equals("")) {
                customer.setId(Integer.parseInt(root.getAttribute("id")));
            }
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
                } else if (element.getTagName().equals("street")) {
                    customer.setStreet(element.getTextContent());
                } else if (element.getTagName().equals("city")) {
                    customer.setCity(element.getTextContent());
                } else if (element.getTagName().equals("state")) {
                    customer.setState(element.getTextContent());
                } else if (element.getTagName().equals("zip")) {
                    customer.setZip(element.getTextContent());
                } else if (element.getTagName().equals("country")) {
                    customer.setCountry(element.getTextContent());
                }
            }
            return customer;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}