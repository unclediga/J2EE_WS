package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
//import javax.inject.Singleton;

@Path("{path}/inject")
//@Singleton     ERROR on bootstrap
public class MyInject {

    @PathParam("path")
    private String p; 

    @QueryParam("query")
    private String q; 

    @GET
    public String getIt() {
        return String.format("path=%s query=%s",p, q);
    }
}
