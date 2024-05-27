package com.example.ChatApplication.Entities;

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
