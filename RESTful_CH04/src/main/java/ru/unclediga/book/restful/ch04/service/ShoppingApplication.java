package ru.unclediga.book.restful.ch04.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ShoppingApplication extends Application {

    private final HashSet<Object> singletons = new HashSet<>();

    public ShoppingApplication() {
        singletons.add(new CustomerDatabaseResource());
        singletons.add(new Echo());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
