package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.inject.Singleton;

@Path("count")
@Singleton
public class Counter {
    
    private int counter1 = 0;
    private static int counter2 = 0;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return String.format("counter1 = %d counter2 = %d", ++counter1, ++counter2);
    }
}
