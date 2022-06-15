package ru.unclediga.book.restful.ch06.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Path("/handler")
public class ContentHandlerService {

    @Path("/so")
    @GET
    public StreamingOutput getStreamOutput(@QueryParam("val") String val) {
        return output -> output.write(val.getBytes());
    }

    @Path("/is")
    @PUT
    public InputStream getInputStream(InputStream is) {
        return is;
    }

    @Path("/rd")
    @GET
    public Reader testReader(@QueryParam("val") String val) {
        return new StringReader(val);
    }

    @Path("/fis/{file_name:.*}")
    @GET
    public InputStream getFileInputStream(@PathParam("file_name") String fileName) {
        final String file = getClass().getClassLoader().getResource(fileName).getFile();
//        System.out.println("file = " + file);
        final FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new WebApplicationException(e.getMessage());
        }
        return fis;
    }

    @Path("/firstline")
    @PUT
    public File getFirstLine(File file) {
//        System.out.println("file = " + file);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File file2 = new File("ttt.txt");

        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file2);
            int b = fis.read();
            while (b != -1 && b != 13 && b != 10) {
                fos.write(b);
                b = fis.read();
            }
            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new WebApplicationException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {

                }
            }
            if (fos != null)
                try {
                    fos.close();
                } catch (Exception e) {

                }
        }
        return file2;
    }

    @Path("/form")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public MultivaluedMap<String, String> testForm(MultivaluedMap<String, String> m) {
        System.out.println(m.size());
        return m;
    }

    @Path("/trans")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String testForm(Source x) {
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
        StringWriter writer = new StringWriter();

        javax.xml.transform.TransformerFactory tFactory =
                javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer(
                    new StreamSource(new StringBufferInputStream(t)));
            transformer.transform(x,
                    new javax.xml.transform.stream.StreamResult(writer));
        } catch (TransformerException e) {
            throw new WebApplicationException(e.getMessage());
        }
        return writer.toString();
    }
}
