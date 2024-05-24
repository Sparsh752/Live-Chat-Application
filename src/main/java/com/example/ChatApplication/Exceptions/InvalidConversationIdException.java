package com.example.ChatApplication.Exceptions;

public class InvalidConversationIdException extends RuntimeException{
    public InvalidConversationIdException(){
        super("The conversationID is invalid.");
    }
}
