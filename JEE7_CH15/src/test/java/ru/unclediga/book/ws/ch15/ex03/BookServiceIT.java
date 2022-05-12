package ru.unclediga.book.ws.ch15.ex03;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class BookServiceIT  {

  private static URI uri = UriBuilder.fromUri("http://localhost").port(8282).build();

  private static HttpServer server;

  @BeforeClass
  public static void init() throws Exception{
    System.out.printf("scheme[%s] host[%s] port[%s] path[%s]",uri.getScheme(),uri.getHost(),uri.getPort(),uri.getPath());
    // http://localhost:8282

    server = HttpServer.create(new InetSocketAddress(uri.getHost(), uri.getPort()), 0);
    // server = HttpServer.create(new InetSocketAddress("localhost", 8282), 0);
    HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ApplicationConfig03(), HttpHandler.class);

    server.createContext("/rs",handler);
    server.start();
  }

  @AfterClass
  public static void stop(){
    server.stop(0);
  }

  @Test
  public void t11() throws java.net.URISyntaxException{
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs/book");
    Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);
    Response response = builder.get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    URI uri = new URI("http://localhost:8282/rs/book");
    response = client.target(uri).request(MediaType.TEXT_PLAIN).get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void t12(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs/book");
    Invocation invocation = target.request(MediaType.TEXT_PLAIN).buildGet();
    Response response = invocation.invoke();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void t13(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs/book");
    Response response = target.request(MediaType.TEXT_PLAIN).get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    String entity = response.readEntity(String.class);
    assertEquals("H2G2", entity);
    assertEquals("H2G2 is 4 chars", 4, entity.length());
  }

  @Test
  public void t14(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs/book");
    Response response = target.request("text/plain").get();
    assertEquals(200, response.getStatus());
    assertTrue(response.hasEntity());
    assertEquals("H2G2", response.readEntity(String.class));
  }

  @Test
  public void t15(){
    Client client = ClientBuilder.newClient();
    String entity = client
                          .target(uri)
                          .path("rs")
                          .path("book")
                          .request(MediaType.TEXT_PLAIN)
                          .get(String.class);
    assertEquals("H2G2", entity);
  }


  @Test
  public void t2(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs");
    Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);

    Response response = builder.get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void t3(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/");
    Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);

    Response response = builder.get();
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }
}