package ru.unclediga.javabrains.jaxrs.exception;

import ru.unclediga.javabrains.jaxrs.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(), 404, "http:\\\\unclediga.ru");
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(message)
                .build();
    }
}
