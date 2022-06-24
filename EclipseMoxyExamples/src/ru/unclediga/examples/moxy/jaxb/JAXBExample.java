package ru.unclediga.examples.moxy.jaxb;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import ru.unclediga.examples.moxy.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class JAXBExample {
    public static void main(String[] args) throws Exception{

        StringWriter writer = new StringWriter();
        Message m1 = new Message(1L, "Hello, World!", "unclediga");
            final javax.xml.bind.JAXBContext context = JAXBContext.newInstance(Message.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("eclipselink.media-type", "application/json");

//            JAXBElement<Message> jaxbElement = new JAXBElement<Message>(new QName(null, "message"), Message.class, m1);
            marshaller.marshal(m1, writer);
//            marshaller.marshal(jaxbElement, writer);
            final String x = writer.toString();
            System.out.println("moxy-1=" + x);
    }
}
