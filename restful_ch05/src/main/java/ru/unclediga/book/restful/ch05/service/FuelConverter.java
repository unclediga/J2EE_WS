package ru.unclediga.book.restful.ch05.service;

import javax.ws.rs.ext.ParamConverter;

public class FuelConverter implements ParamConverter<CarResource.FuelType> {
    @Override
    public CarResource.FuelType fromString(String value) {
        return CarResource.FuelType.valueOf(value.toUpperCase());
    }

    @Override
    public String toString(CarResource.FuelType value) {
        return value.toString();
    }
}
