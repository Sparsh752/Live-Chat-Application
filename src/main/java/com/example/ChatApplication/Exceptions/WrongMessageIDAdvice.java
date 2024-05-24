package com.example.ChatApplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class WrongMessageIDAdvice {

    @ExceptionHandler(WrongMessageIDException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(WrongMessageIDException ex) {
        return ex.getMessage();
    }
}
