package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Base64;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext context) {
        System.out.println(context.getUriInfo().getPath());
        if (context.getUriInfo().getPath().endsWith("secured")) {
            final List<String> header = context.getHeaders().get("Authorization");
            if (header != null && header.size() > 0) {
                String encoded = header.get(0).replace("Basic ", "");
                final String pass = new String(Base64.getDecoder().decode(encoded));
                if (pass.equals("user:password")) {
                    return;
                }
            }
        } else {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access Deny!").build());

        }
    }
}
