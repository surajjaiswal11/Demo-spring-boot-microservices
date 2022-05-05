package com.data.exception;

import java.util.Date;

import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse unKnownException(Exception ex) {

        return new ErrorResponse(new Date(), "Not found", ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerException(Exception ex) {

        return new ErrorResponse(new Date(), "Internal server error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}