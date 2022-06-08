package ru.unclediga.book.restful.ch06.service;

import ru.unclediga.book.restful.ch06.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/customers")
public class CustomerService {
    private final Customer PETER_PEN = Customer.getPeterPan();
    private final Customer WENDY_DARLING = Customer.getWendyDarling();
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getCustomer(@PathParam("id") int customer_id){
        switch (customer_id){
            case 1: return Customer.getPeterPan();
            case 2: return Customer.getWendyDarling();
        }
        return null;
    }

    @POST
    @Path("not_rest")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Customer postCustomer_NOT_REST(Customer customer){
        if (customer == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if(customer.getFullName().equals(PETER_PEN.getFullName())){
            customer.setId(1);
        }else if(customer.getFullName().equals(WENDY_DARLING.getFullName())){
            customer.setId(2);
        }
        return customer;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response postCustomer_REST(Customer customer){
        int id = 0;
        if (customer == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if(customer.getFullName().equals(PETER_PEN.getFullName())){
            id = 1;
        }else if(customer.getFullName().equals(WENDY_DARLING.getFullName())){
            id = 2;
        }
        return Response.created(URI.create("/customers/" + id)).build();
    }
}
