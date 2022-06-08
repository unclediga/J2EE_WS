package ru.unclediga.book.restful.ch04.service;

import ru.unclediga.book.restful.ch06.service.CustomerService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class CustomerApplication extends Application {


    private final Set<Object> single = new HashSet<>();

    public CustomerApplication() {
        single.add(new CustomerService());
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
