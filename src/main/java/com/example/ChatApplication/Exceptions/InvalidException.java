package com.example.ChatApplication.Exceptions;

// This class is used to represent the invalid exception
public class InvalidException extends RuntimeException {
    public InvalidException(String item) {
        super("The " + item + " is invalid.");
    }
}
