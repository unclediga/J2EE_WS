package ru.unclediga.book.restful.ch05.service;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class FuelConverterProvider implements ParamConverterProvider {
    private final ParamConverter converter = new FuelConverter();
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(rawType.equals(CarResource.FuelType.class))
            return converter;
        return null;
    }
}
