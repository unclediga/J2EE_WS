package ru.unclediga.book.restful.ch06.service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

@Path("/fs")
public class ContextService {

    @GET
    @Path("/echo")
    @Produces("text/plain")
    public String getEcho() {
        return "send ECHO from /fs/echo";
    }

    @GET
    @Path("/info")
    @Produces("text/plain")
    public String getUriInfo(@Context UriInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("getRequestUri:").append(info.getRequestUri()).append('\n');
        sb.append("getAbsolutePath:").append(info.getAbsolutePath()).append('\n');
        sb.append("getPath:").append(info.getPath()).append('\n');
        sb.append("getBaseUri:").append(info.getBaseUri()).append('\n');
        sb.append("getMatchedURIs: --->").append('\n');
        for (String r : info.getMatchedURIs())
            sb.append("   :").append(r).append('\n');
        sb.append("getMatchedResources: --->").append('\n');
        for (Object r : info.getMatchedResources())
            sb.append("   :").append(r).append('\n');
        return "/INFO:\n" + sb;
    }


    @GET
    @Path("/file")
    @Produces(MediaType.TEXT_PLAIN)
    public File getFileContent(@QueryParam("file_name") String file_name,
                               @Context ServletContext context) {
        System.out.println("context path=" + context.getContextPath());
        System.out.println("server info=" + context.getServerInfo());

        String realPath = context.getRealPath(file_name);
        final Set<String> paths = context.getResourcePaths("\\restful_ch06\\target\\restful_ch06\\content.txt");
        for(String path : paths){
            System.out.println(path);
        }
        if (context.getContextPath().equals("/jersey/test")){
            realPath = realPath.replace("restful_ch06", "target\\restful_ch06");
        }
        System.out.println("realPath=" + realPath);
        final File file = new File(realPath);
        return file;
    }
}
