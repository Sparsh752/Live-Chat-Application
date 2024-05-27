package com.example.ChatApplication.Exceptions;

public class MissingException extends RuntimeException{
    public MissingException(String item){
        super(item+" is missing.");
    }
}
