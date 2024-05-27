package com.example.ChatApplication.Entities;

public class HandshakeResponse {
    private String appID;
    private String chatSessionToken;
    public HandshakeResponse(){}
    public HandshakeResponse(String appID){
        this.appID=appID;
        this.chatSessionToken=null;
    }
    public HandshakeResponse(String appID, String chatSessionToken){
        this.appID=appID;
        this.chatSessionToken=chatSessionToken;
    }

    public String getAppID() {
        return appID;
    }

    public String getChatSessionToken() {
        return chatSessionToken;
    }
}
