package ru.unclediga.book.wsuprun.ch01;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;



public class PredictionsTest {

    NanoSrv server;

    @Before
    public void setUp() throws IOException{
        System.out.println("Start NanoHTTPD...");
        server = new NanoSrv();
        System.out.println("...done!");
    }

    @After    
    public void dropDown() {
        System.out.println("Close NanoHTTPD...");
        if (server != null){
            server.closeAllConnections();
            server.stop();
        }
        System.out.println("...done!");
    }

    @Test
    public void testClient1() throws IOException{
        System.out.print("open socket...");
        Socket s = new Socket("localhost",8081);
        System.out.println("done");
        System.out.print("send req...");
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.write("GET / HTTP/1.1\n");
        out.write("host: localhost");
        out.write("Connection: close");
        
        out.write("\n\n");
        out.flush();
        System.out.println("done");


        System.out.print("read res...");
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String response;
        while((response = in.readLine()) != null){
            sb.append(response);
        }
        response = sb.toString();
        System.out.println("done");
        System.out.println("RESPONSE =>" + response);
        s.close();

        assertTrue(response.startsWith("HTTP/1.1 200 OK"));
        assertTrue(response.endsWith(NanoSrv.RESP));

    }

    @Test
    public void testClient2() throws IOException {
        URL url = new URL("http://localhost:8081");
        URLConnection s = url.openConnection();
        System.out.println("ContentType =>" + s.getContentType());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String response;
        while((response = in.readLine()) != null){
            sb.append(response);
        }
        response = sb.toString();
        System.out.println("RESPONSE =>" + response);

        assertTrue(response.startsWith(NanoSrv.RESP));
    }

}