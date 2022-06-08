package ru.unclediga.book.restful.ch06.domain;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    @XmlAttribute
    private int id;
    @XmlElement
    private String fullName;
    @XmlElement
    private Address address;

    public static Customer getPeterPan() {
        Customer p = new Customer();
        p.id = 1;
        p.fullName = "Peter Pan";
        p.address = new Address("Big City", "Peace st");
        return p;
    }

    public static Customer getWendyDarling() {
        Customer p = new Customer();
        p.id = 2;
        p.fullName = "Wendy Darling";
        p.address = new Address("Big City", "Kensington Gardens");
        return p;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id[" + id + "]" +
                " fullName[" + fullName + "]" +
                " address[" + address + "]}";
    }

    public String toXML() {
        return String.format("<customer id=\"%s\"><fullName>%s</fullName><address><city>%s</city><street>%s</street></address></customer>",
                this.id, this.fullName, address.getCity(), address.getStreet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id
                && fullName.equals(customer.fullName)
                && Objects.equals(address.getCity(), customer.address.getCity())
                && Objects.equals(address.getStreet(), customer.address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, address);
    }
}