package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

@Provider
public class MyInterceptor implements WriterInterceptor {
    @Context
    private UriInfo info;

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws WebApplicationException, IOException {

        if (info.getRequestUri().getPath().endsWith("myresource")
                && context.getMediaType().isCompatible(MediaType.TEXT_PLAIN_TYPE)) {
                String message = (String) context.getEntity();
                context.setEntity("Intercepted:" + message);
                context.proceed();
        } else {
            context.proceed();
        }
    }
}
