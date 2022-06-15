package ru.unclediga.book.restful.ch06.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
@ApplicationPath("/services")
public class CustomerApplication extends Application {


    private final Set<Object> single = new HashSet<>();

    public CustomerApplication() {
        single.add(new CustomerService());
        single.add(new ContextService());
        single.add(new ContentHandlerService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return null;
    }

    @Override
    public Set<Object> getSingletons() {
        return single;
    }
}
