package com.example.ChatApplication.Exceptions;

public class MissingAppIdException extends RuntimeException{
    public MissingAppIdException(){
        super("App Id is missing.");
    }
}
