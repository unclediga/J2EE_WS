package ru.unclediga.book.restful.ch06.service;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.unclediga.book.restful.ch06.domain.Customer;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ContentHandlerTest {


    private final Customer PETER_PEN = Customer.getPeterPan();
    private final Customer WENDY_DARLING = Customer.getWendyDarling();
    private final int OK200 = Response.Status.OK.getStatusCode();

    private static HttpServer server;
    private static WebTarget rootTarget;

    @BeforeClass
    public static void init() throws IOException {

        final HttpHandler httpHandler = RuntimeDelegate.getInstance()
                .createEndpoint(new CustomerApplication(), HttpHandler.class);
        server = HttpServer.create(new InetSocketAddress("localhost", 7778), 0);
        server.createContext("/", httpHandler);
        server.start();
        //System.out.println(server.getClass().getClassLoader().getResource("."));
        rootTarget = ClientBuilder.newClient().target("http://localhost:7778").path("handler");

    }

    @AfterClass
    public static void shutdown() {
        server.stop(0);
    }

    @Test
    public void testStreamOutput() {
        final Response response = rootTarget
                .path("so")
                .queryParam("val", "STREAM_OUTPUT")
                .request(MediaType.TEXT_PLAIN)
                .get();
        assertEquals(OK200, response.getStatus());
        assertEquals("STREAM_OUTPUT", response.readEntity(String.class));
    }

    @Test
    public void testInputStream() throws IOException {
        final Response response = rootTarget
                .path("is")
                .request(MediaType.TEXT_PLAIN)
                .put(Entity.text("ABCD12345"));
        assertEquals(OK200, response.getStatus());
        final InputStream is = response.readEntity(InputStream.class);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();
        assertNotNull(line);
        assertEquals("ABCD12345", line);
    }

    @Test
    public void testReader() throws IOException {
        final Response response = rootTarget
                .path("rd")
                .queryParam("val", "ABCD12345")
                .request(MediaType.TEXT_PLAIN)
                .get();
        assertEquals(OK200, response.getStatus());
        final BufferedReader reader = new BufferedReader(response.readEntity(Reader.class));
        final List<String> list = reader.lines().collect(Collectors.toList());
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("ABCD12345", list.get(0));
    }

    @Test
    public void testFile() throws IOException {
        String fileName = getClass().getClassLoader().getResource("content.txt").getFile();
        final File file = new File(fileName);
        assertNotNull(file);
    }

    @Test
    public void testFile2() throws IOException {
        final Response response = rootTarget
                .path("fis")
                .path("content.txt")
                .request(MediaType.TEXT_PLAIN)
                .get();
        assertEquals(OK200, response.getStatus());
        final String CONTENT = "1. Hello2. World3. !!!";
        final InputStream entity = response.readEntity(InputStream.class);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(entity));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        assertNotNull(sb.toString());
        assertEquals(CONTENT, sb.toString());
    }

    @Test
    public void testFile3() throws IOException {
        String fileName = getClass().getClassLoader().getResource("content.txt").getFile();
        final File file = new File(fileName);
        assertNotNull(file);
        final Response response = rootTarget
                .path("firstline")
                .request(MediaType.TEXT_PLAIN)
                .put(Entity.entity(file, MediaType.TEXT_PLAIN));

        assertEquals(OK200, response.getStatus());
        final String CONTENT = "1. Hello";
        final InputStream entity = response.readEntity(InputStream.class);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(entity));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        assertNotNull(sb.toString());
        assertEquals(CONTENT, sb.toString());
    }

    @Test
    public void testFormMultiMap() {
        MultivaluedMap<String, String> m = new MultivaluedHashMap<>();
        m.add("par1", "val1");
        m.add("par2", "val2");
        final Response response = rootTarget.path("form")
                .request()
//                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(m));

        final MultivaluedMap<String, String> mm = (MultivaluedMap<String, String>) response.readEntity(MultivaluedMap.class);
//
        assertEquals(200, response.getStatus());
        assertEquals("val1", mm.getFirst("par1"));
        assertEquals("val2", mm.getFirst("par2"));
        assertEquals(2, mm.size());

    }

    @Test
    public void testTransform() throws TransformerException {

        String t = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xsl:stylesheet version=\"1.0\"\n" +
                "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "<xsl:output method=\"xml\" version=\"1.0\" indent=\"no\"/>" +
                "<xsl:template match=\"/\">\n" +
                "<html>\n" +
                "<body>\n" +
                "<table>\n" +
                "<xsl:for-each select=\"catalog/moto\">\n" +
                "<tr>\n" +
                "<td><xsl:value-of select=\"title\"/></td>\n" +
                "<td><xsl:value-of select=\"color\"/></td>\n" +
                "</tr>\n" +
                "</xsl:for-each>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n" +
                "</xsl:template>\n" +
                "</xsl:stylesheet>";

        String x = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<catalog>\n" +
                "<moto><title>Aprilia</title><color>red</color></moto>\n" +
                "<moto><title>Honda</title><color>blue</color></moto>\n" +
                "</catalog>";

        javax.xml.transform.TransformerFactory tFactory =
                javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer =
                tFactory.newTransformer(
                        new StreamSource(new StringBufferInputStream(t)));
        StringWriter writer = new StringWriter();

        transformer.transform(new StreamSource(new StringBufferInputStream(x)),
                new javax.xml.transform.stream.StreamResult(writer));

        String o = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html><body><table><tr><td>Aprilia</td><td>red</td></tr><tr><td>Honda</td><td>blue</td></tr></table></body></html>";

        assertEquals(o, writer.toString());
    }

    @Test
    public void testTransformRS() {

        String x = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<catalog>\n" +
                "<moto><title>Aprilia</title><color>red</color></moto>\n" +
                "<moto><title>Honda</title><color>blue</color></moto>\n" +
                "</catalog>";

//        final Response response = rootTarget.path("trans")
//                .request(MediaType.APPLICATION_XML)
//                .post(new StreamSource(new StringBufferInputStream(x)));


        String o = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html><body><table><tr><td>Aprilia</td><td>red</td></tr><tr><td>Honda</td><td>blue</td></tr></table></body></html>";

//        assertEquals(o, writer.toString());
    }
}
