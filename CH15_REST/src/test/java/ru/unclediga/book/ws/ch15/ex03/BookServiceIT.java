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

public class BookServiceIT  {

  private static InetSocketAddress uri = new InetSocketAddress(8282);
  private static HttpServer server;

  @BeforeClass
  public static void init() throws Exception{
    server = HttpServer.create(uri, 0);
    HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ApplicationConfig03(), HttpHandler.class);

    server.createContext("http://localhost:8282",handler);
    server.start();
  }

  @AfterClass
  public static void stop(){
    server.stop(0);
  }

  @Test
  public void t1(){

  }
}