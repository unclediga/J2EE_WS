package ru.unclediga.javabrains.jaxrs.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
}
