package ru.unclediga.javabrains.jaxrs.resources;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JAXBTest {


    @Test
    public void testJAXB() throws JAXBException {
        Message m1 = new MessageService().getAllMessages().get(0);
        final JAXBContext context = JAXBContext.newInstance(Message.class);
        StringWriter writer = new StringWriter();
        context.createMarshaller().marshal(m1, writer);

        final String x = writer.toString();
        assertTrue(x.contains("<message>"));
        System.out.println("x=" + x);
    }

    @Test
    public void testMOXY() throws JAXBException {

        StringWriter writer = new StringWriter();

        Message m1 = new MessageService().getAllMessages().get(0);
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        JAXBElement<Message> jaxbElement = new JAXBElement<Message>(new QName(null, "message"), Message.class, m1);
        marshaller.marshal(jaxbElement, writer);
        final String x = writer.toString();
        System.out.println("moxy-1=" + x);
    }

    @Test
    public void testDB() {
        final Map<Long, Message> messages = DatabaseClass.getMessages();
        assertEquals(2, messages.size());
    }

    @Test
    public void testJSON() throws JAXBException {

        Message m1 = new Message(1L, "Hello, World!", "unclediga");

        StringWriter writer = new StringWriter();
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("eclipselink.media-type", "application/json");
            JAXBElement<Message> jaxbElement = new JAXBElement<Message>(new QName(null, "message"), Message.class, m1);
            marshaller.marshal(jaxbElement, writer);
            final String x = writer.toString();
            System.out.println("moxy-1=\n" + x);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }


//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        unmarshaller.setProperty("eclipselink.media-type", "application/json");
//        unmarshaller.setProperty("eclipselink.json.include-root", false);
//    StreamSource source = new StreamSource("http://search.twitter.com/search.json?q=jaxb");
//    JAXBElement<SearchResults> jaxbElement = unmarshaller.unmarshal(source, SearchResults.class);
//
//    Result result = new Result();
//        result.setCreatedAt(new Date());
//        result.setFromUser("bsmith");
//        result.setText("You can now use EclipseLink JAXB (MOXy) with JSON :)");
//        jaxbElement.getValue().getResults().add(result);
    }

}
