package com.example.ChatApplication.Exceptions;

// This class is used to represent the user access denied exception
public class UserAccessDeniedException extends RuntimeException {
    public UserAccessDeniedException() {
        super("User not allowed to access the conversation.");
    }
}
