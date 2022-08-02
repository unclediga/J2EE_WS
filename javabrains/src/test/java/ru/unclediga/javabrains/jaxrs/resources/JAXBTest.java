package ru.unclediga.javabrains.jaxrs.resources;

import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        JAXBElement<Message> jaxbElement = new JAXBElement<>(new QName(null, "message"), Message.class, m1);
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
        final String x;
        StringWriter writer = new StringWriter();
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");

        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        try {

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("eclipselink.media-type", "application/json");
            JAXBElement<Message> jaxbElement = new JAXBElement<>(new QName(null, "message"), Message.class, m1);
            marshaller.marshal(jaxbElement, writer);
            x = writer.toString();
            System.out.println("marshal\n" + x);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }


        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty("eclipselink.media-type", "application/json");
            unmarshaller.setProperty("eclipselink.json.include-root", true);
            StringReader reader = new StringReader(x);
            StreamSource source = new StreamSource(reader);
            JAXBElement<Message> jaxbElement = unmarshaller.unmarshal(source, Message.class);
            Message m = jaxbElement.getValue();
            System.out.println("unmarshal\n" + m);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
