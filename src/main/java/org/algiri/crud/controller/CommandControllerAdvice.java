package org.algiri.crud.controller;

import org.algiri.crud.exception.ApplicationError;
import org.algiri.crud.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommandControllerAdvice {
    @Value("${spring.application.name}")
    private String appName;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ApplicationError NotFoundError(NotFoundException exception) {
        return getError(exception);
    }

    private ApplicationError getError(Exception e) {
        return new ApplicationError(appName, e.getMessage());
    }
}
