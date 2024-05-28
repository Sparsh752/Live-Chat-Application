package com.example.ChatApplication.Exceptions;

// This class is used to represent the conversation deleted exception
public class ConversationDeletedException extends RuntimeException{
    public ConversationDeletedException(){
        super("Conversation is deleted.");
    }
}
