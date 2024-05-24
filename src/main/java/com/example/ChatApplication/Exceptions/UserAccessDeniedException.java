package com.example.ChatApplication.Exceptions;

public class UserAccessDeniedException extends RuntimeException{
    public UserAccessDeniedException(){
        super("User not allowed to access the conversation.");
    }
}
