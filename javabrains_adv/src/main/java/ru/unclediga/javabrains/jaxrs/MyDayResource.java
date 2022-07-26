package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/{day}/date")
public class MyDayResource{

  @GET
  public String getDay(@PathParam("day") MyDay d){
    return "Got day: " + d.toString();
  }
}
