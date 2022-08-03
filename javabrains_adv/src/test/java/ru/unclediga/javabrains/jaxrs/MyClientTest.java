package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import static org.junit.Assert.*;

import org.glassfish.jersey.internal.util.Tokenizer;
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
    private final WebTarget baseTarget = client.target("http://0.0.0.0:7778/abc/webapi");

    @Test
    public void testClientInside() {

        Response response = client.target("http://oasboss3/docs.html")
                            .request(MediaType.TEXT_PLAIN)
                            .get();
        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
                              
    }

    @Test
    public void testClientJetty() {


        Response response = baseTarget
                            .path("myresource")
                            .request()
                            .get();
        
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String stringData = response.readEntity(String.class);                            
        System.out.println("STRING[" + stringData + "]");

        response = baseTarget
                .path("myresource")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        MyData data1 = response.readEntity(MyData.class);
        System.out.println("MYDATA1[" + data1 + "]");

         MyData data2 = baseTarget.path("/myresource")
                             .request(MediaType.APPLICATION_JSON)
                             .get(MyData.class);
        System.out.println("MYDATA2[" + data2 + "]");
    }

    @Test
    public void testClientOutside() {
        System.out.println("===>");
        System.out.println();
        String proxy = System.getenv("PROXYSTR");
        if(!proxy.isEmpty()){
            final String[] tokens = Tokenizer.tokenize(proxy, ":");
            System.setProperty("http.proxyHost", tokens[0]);
            System.setProperty("http.proxyPort", tokens[1]);
            System.setProperty("http.auth.ntlm.domain",
                    System.getenv("USERDOMAIN") + "\\"+ System.getenv("USERNAME"));

            System.out.println(System.getProperty("http.proxyHost"));
            System.out.println(System.getProperty("http.proxyPort"));
            System.out.println(System.getProperty("http.auth.ntlm.domain"));
        }

        ClientConfig clientConfig = new ClientConfig()
        .property(ClientProperties.READ_TIMEOUT, "3000")
        .property(ClientProperties.CONNECT_TIMEOUT, "3000");

        Client client = ClientBuilder.newClient(clientConfig);

        System.out.println(client.getConfiguration().getPropertyNames());


        WebTarget baseTarget = client.target("http://httpbin.org/anything");
        Response response = baseTarget.request(MediaType.TEXT_PLAIN).get();
        System.out.println("===>");

        String res = baseTarget.path("/AAA/BBB").request(MediaType.TEXT_PLAIN).get(String.class);
        System.out.println("===>");
        System.out.println(res);
    }

    @Test
    public void testClientPathTemplate() {
        String proxy = System.getenv("PROXYSTR");
        if(!proxy.isEmpty()){
            final String[] tokens = Tokenizer.tokenize(proxy, ":");
            System.setProperty("http.proxyHost", tokens[0]);
            System.setProperty("http.proxyPort", tokens[1]);
            System.setProperty("http.auth.ntlm.domain",
                    System.getenv("USERDOMAIN") + "\\"+ System.getenv("USERNAME"));
        }

        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, "3000")
                .property(ClientProperties.CONNECT_TIMEOUT, "3000");

        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget baseTarget = client.target("http://httpbin.org/anything");
        WebTarget templateTarget = baseTarget.path("{a}").path("{b}");
        Response response = templateTarget
                .resolveTemplate("a","ABC")
                .resolveTemplate("b","DEF")
                .request(MediaType.TEXT_PLAIN).get();
        final String entity = response.readEntity(String.class);
        assertTrue(entity.contains("\"url\": \"http://httpbin.org/anything/ABC/DEF\""));
    }

    @Test
    public void testClientPost() {
        final String txt = "data for request";
        MyData d = new MyData(txt);
        Response response = baseTarget
                .path("myresource")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(d));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        MyData data = response.readEntity(MyData.class);
        assertEquals(data.getValue(), "POST:" + txt);
    }

    @Test
    public void testClientPostREST() {
        final String txt = "data for request";
        MyData d = new MyData(txt);
        Response response = baseTarget
                .path("myresource")
                .path("rest")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(d));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(UriBuilder.fromUri(baseTarget.getUri()).path("/myresource/rest").build(), response.getLocation());
        MyData data = response.readEntity(MyData.class);
        assertEquals(data.getValue(), "POST:" + txt);
    }

    @Test
    public void testClientPut() {
        final String txt = "data for request";
        MyData d = new MyData(txt);
        Response response = baseTarget
                .path("myresource")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(d));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

}