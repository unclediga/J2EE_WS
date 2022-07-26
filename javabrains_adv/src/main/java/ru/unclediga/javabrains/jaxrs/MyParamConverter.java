package ru.unclediga.javabrains.jaxrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.ParamConverter;


@Provider
public class MyParamConverter implements ParamConverterProvider {

  @Override
  public <T> ParamConverter<T> getConverter(Class<T> rawType,Type genericType, Annotation[] annotations){ 
      
      return new ParamConverter<T>(){

          @Override
          public T fromString(String value){
            MyDay d = new MyDay();
            if("tomorrow".equals(value)){
              d.incDay();
            } else if("yesterday".equals(value)){
              d.decDay();
            }
            return rawType.cast(d);
          }

          @Override
          public String toString(T value){
            if(value == null){
              return null;
            }
            return value.toString();
          }
    };  
  }
}
