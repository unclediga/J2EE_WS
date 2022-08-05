package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Base64;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext context) {
        if (context.getUriInfo().getPath().endsWith("secured")) {
            final String header = context.getHeaders().getFirst("Authorization");
            if (header != null) {
                String encoded = header.replace("Basic ", "");
                final String pass = new String(Base64.getDecoder().decode(encoded));
                if (!pass.equals("user:password")) {
                    context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access Deny!").build());
                }
            } else {
                context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access Deny!").build());
            }
        }
    }
}
