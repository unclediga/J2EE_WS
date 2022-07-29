package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.*;
import org.junit.Test;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientConfig;

public class MyClientTest {
    /*



{
  "args": {}, 
  "data": "", 
  "files": {}, 
  "form": {}, 
  "headers": { }, 
  "json": null, 
  "method": "GET", 
  "origin": "193...", 
  "url": "http://httpbin.org/anything/assa/BB"
}
    */

    private final Client client = ClientBuilder.newClient();
    private final WebTarget baseTarget = client.target("http://0.0.0.0:7778/webapi");

    @Test
    public void testClientInside() {


        Response response = client.target("http://oasboss3/docs.html")
                            .request(MediaType.TEXT_PLAIN)
                            .get();
        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
                              
    }

    @Test
    public void testClientJetty() {


        Response response = client.target("http://127.0.0.1:7778/abc/webapi/myresource")
                            // .path("myresource")
                            .request()
                            .get();
        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String stringData = response.readEntity(String.class);                            
        System.out.println("STRING");
        System.out.println("[" + stringData + "]");
                                  
        // MyData d = baseTarget
        //                     .path("/myresource")
        //                     .request(MediaType.APPLICATION_JSON)
        //                     .get(MyData.class);
        // System.out.println(d);
    }

    public void testClientOutside() {
        System.out.println("===>");


        // client.property(ClientProperties.PROXY_URI,"iwsva.avangard.ru:8080");
        // client.property(ClientProperties.PROXY_USERNAME,"BorodinovI");
        // client.property(ClientProperties.PROXY_PASSWORD,"Jrnz,hmcrfz25");

        ClientConfig clientConfig = new ClientConfig()
        .property(ClientProperties.READ_TIMEOUT, "30000")
        .property(ClientProperties.CONNECT_TIMEOUT, "30000")
        .property(ClientProperties.PROXY_URI, "iwsva.avangard.ru:8080");

        Client client = ClientBuilder.newClient(clientConfig);

        System.out.println(client.getConfiguration().getPropertyNames());


        WebTarget baseTarget = client.target("http://httpbin.org/anything");
        //WebTarget baseTarget = client.target("http://avangard.ru");
        Response response = baseTarget.request(MediaType.TEXT_PLAIN).get();
        System.out.println("===>");

        String res = baseTarget.path("/AAA/BBB").request(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println("===>");
        System.out.println(res);

    }
}