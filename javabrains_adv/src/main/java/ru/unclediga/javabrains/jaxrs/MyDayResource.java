package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public class MyDayResource{

  @GET
  @Path("/{day}/date")
  public String getDay(@PathParam("day") MyDay d){
    return "Got day: " + d.toString();
  }

  @GET
  @Path("/today")
  @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
  public MyDay getDayAsMessage(){
    return new MyDay();
  }
}
