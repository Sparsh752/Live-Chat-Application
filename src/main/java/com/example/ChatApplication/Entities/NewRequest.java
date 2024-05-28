package com.example.ChatApplication.Entities;

// This class is used to represent the new request
public class NewRequest {
    private String chatSessionToken;
    public NewRequest(){};
    public NewRequest(String chatSessionToken){
        this.chatSessionToken=chatSessionToken;
    }

    public String getChatSessionToken() {
        return chatSessionToken;
    }

    public void setChatSessionToken(String chatSessionToken) {
        this.chatSessionToken = chatSessionToken;
    }
}
