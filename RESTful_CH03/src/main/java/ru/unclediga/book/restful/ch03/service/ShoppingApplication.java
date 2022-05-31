package ru.unclediga.book.restful.ch03.service;

import ru.unclediga.book.restful.ch03.service.CustomerService;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/services")
public class ShoppingApplication extends Application {
    private Set<Class<?>> classes;

    ShoppingApplication() {
        Set<Class<?>> s = new HashSet<>();
        s.add(CustomerService.class);
        classes = Collections.unmodifiableSet(s);
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }
}