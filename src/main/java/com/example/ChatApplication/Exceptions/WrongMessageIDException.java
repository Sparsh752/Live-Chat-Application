package com.example.ChatApplication.Exceptions;

public class WrongMessageIDException extends RuntimeException{
    public WrongMessageIDException(){
        super("Wrong Message ID.");
    }
}
