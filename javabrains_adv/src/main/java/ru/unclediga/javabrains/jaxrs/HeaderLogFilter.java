package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class HeaderLogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private String headerIn;

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        headerIn = reqContext.getHeaders().getFirst("Header-In");
        System.out.println("REQ HEADER-IN:" + headerIn);
    }

    @Override
    public void filter(ContainerRequestContext reqContext, ContainerResponseContext respContext) throws IOException {
        String headerIn = reqContext.getHeaders().getFirst("Header-In");
        respContext.getHeaders().add("Header-Out-Req", this.headerIn);
        respContext.getHeaders().add("Header-Out-Resp", headerIn);
        System.out.println("RESP HEADER-IN (fld):" + this.headerIn);
        System.out.println("RESP HEADER-IN (var):" + headerIn);
    }
}
