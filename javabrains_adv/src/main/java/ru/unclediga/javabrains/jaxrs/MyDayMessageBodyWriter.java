package ru.unclediga.javabrains.jaxrs;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.io.OutputStream;
import java.io.IOException;

import static ru.unclediga.javabrains.jaxrs.MyDayResource.APPLICATION_MYDAY;
import static ru.unclediga.javabrains.jaxrs.MyDayResource.APPLICATION_MYDAY_TYPE;

@Provider
@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN, APPLICATION_MYDAY})
public class MyDayMessageBodyWriter implements MessageBodyWriter<MyDay>{
    @Override
    public long getSize(MyDay t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType){
        return -1;
    }
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType){
        if(mediaType.equals(MediaType.APPLICATION_XML_TYPE) 
            || mediaType.equals(MediaType.TEXT_PLAIN_TYPE)
            || mediaType.equals(APPLICATION_MYDAY_TYPE)){
            if(type.equals(MyDay.class)){
                return true;
            }
        }
        return false;

    }
    @Override
    public void writeTo(MyDay t, 
                 Class<?> type, 
                 Type genericType, 
                 Annotation[] annotations, 
                 MediaType mediaType, 
                 MultivaluedMap<String,Object> httpHeaders, 
                 OutputStream entityStream) throws IOException{

        if(mediaType.equals(MediaType.APPLICATION_XML_TYPE)){
            entityStream.write(t.toXml().getBytes());
            //entityStream.close();  
            /* JavaEE 7 docs : 
             "entityStream - the OutputStream for the HTTP entity. 
             The implementation should not close the output stream."
            */ 
        }else if (mediaType.equals(MediaType.TEXT_PLAIN_TYPE)){
            entityStream.write(t.toString().getBytes());
        }else if (mediaType.equals(APPLICATION_MYDAY_TYPE)){
            entityStream.write(t.toMediaType().getBytes());
        }
    }
}