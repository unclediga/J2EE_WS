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
    System.out.println("path:" + uri.getPath() + " port:" + uri.getPort());
    server = HttpServer.create(new InetSocketAddress("localhost", 8282), 0);
    // server = HttpServer.create(new InetSocketAddress(uri.getPath(), uri.getPort()), 0);
    HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ApplicationConfig03(), HttpHandler.class);

    server.createContext("/rs",handler);
    server.start();
  }

  @AfterClass
  public static void stop(){
    server.stop(0);
  }

  @Test
  public void t1(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs/book");
    Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN);

    Response response = builder.get();
    // assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    String entity = response.readEntity(String.class);
    assertEquals("H2G2", entity);
  }

  @Test
  public void t2(){
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8282/rs");
    Invocation.Builder builder = target.request(MediaType.TEXT_HTML);

    Response response = builder.get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    // assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
  }
}