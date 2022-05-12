package ru.unclediga.book.ws.ch15.ex03;

import javax.ws.rs.Path;
import javax.ws.rs.GET;

@Path("/")
public class RootRestService {
  @GET
  public String getRoot(){
    return "ROOT FOR YOU!";
  }
}