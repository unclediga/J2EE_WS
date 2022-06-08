import org.junit.Test;
import static org.junit.Assert.*;
import ru.unclediga.book.restful.ch06.domain.Customer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXBTest {

    private final Customer PETER_PEN = Customer.getPeterPan();
    private final Customer WENDY_DARLING = Customer.getWendyDarling();

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
}
