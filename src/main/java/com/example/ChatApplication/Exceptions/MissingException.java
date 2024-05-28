package com.example.ChatApplication.Exceptions;

// This class is used to represent the missing exception
public class MissingException extends RuntimeException{
    public MissingException(String item){
        super(item+" is missing.");
    }
}
