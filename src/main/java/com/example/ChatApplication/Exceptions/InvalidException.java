package com.example.ChatApplication.Exceptions;

public class InvalidException extends RuntimeException{
    public InvalidException(String item){
        super("The "+item+" is invalid.");
    }
}
