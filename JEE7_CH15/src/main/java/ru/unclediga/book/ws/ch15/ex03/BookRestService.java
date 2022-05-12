package ru.unclediga.book.ws.ch15.ex03;

import javax.ws.rs.Path;
import javax.ws.rs.GET;

@Path("/book")
public class BookRestService {
  @GET
  public String getBookTitle(){
    return "H2G2";
  }
}