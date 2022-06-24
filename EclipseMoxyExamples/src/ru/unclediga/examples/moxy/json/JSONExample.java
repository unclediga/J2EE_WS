package ru.unclediga.examples.moxy.json;

import ru.unclediga.examples.moxy.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

public class JSONExample {
    public static void main(String[] args) {

        StringWriter writer = new StringWriter();
//        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");

        Message m1 = new Message(1L, "Hello, World!", "unclediga");
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("eclipselink.media-type", "application/json");
            JAXBElement<Message> jaxbElement = new JAXBElement<Message>(new QName(null, "message"), Message.class, m1);
            marshaller.marshal(jaxbElement, writer);
            final String x = writer.toString();
            System.out.println("moxy-1=" + x);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}

