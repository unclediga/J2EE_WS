package ru.unclediga.javabrains.jaxrs.resources;

import org.junit.Test;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.service.MessageService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class MessageResourceTest {


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
}
