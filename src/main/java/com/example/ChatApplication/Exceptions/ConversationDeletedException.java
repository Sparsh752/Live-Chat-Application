package com.example.ChatApplication.Exceptions;

public class ConversationDeletedException extends RuntimeException{
    public ConversationDeletedException(){
        super("Conversation is deleted.");
    }
}
