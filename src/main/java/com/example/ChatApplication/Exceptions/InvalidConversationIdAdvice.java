package com.example.ChatApplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InvalidConversationIdAdvice {

    @ExceptionHandler(InvalidConversationIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(InvalidConversationIdException ex) {
        return ex.getMessage();
    }
}