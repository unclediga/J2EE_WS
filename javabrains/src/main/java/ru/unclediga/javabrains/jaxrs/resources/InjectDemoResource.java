package ru.unclediga.javabrains.jaxrs.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Map;

@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

    @GET
    @Path("/annotations")
    public String getParamsUsingAnnotations(@MatrixParam("mxParam1") String matrixParam,
                                            @MatrixParam("mxParam2") List<String> matrixParams,
                                            @HeaderParam("mySessionId") String sid,
                                            @CookieParam("myName") String cookie) {
        return "[matrix param: " + matrixParam + "]\n" +
                "[matrix params: " + matrixParams + "]\n" +
                "[header param: " + sid + "]\n" +
                "[cookie: " + cookie + "]";
    }

    @GET
    @Path("context")
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        final String path = uriInfo.getAbsolutePath().toString();
        final Map<String, Cookie> cookies = headers.getCookies();
        return "Path:" + path +  " cookie:" + cookies;
    }
}
