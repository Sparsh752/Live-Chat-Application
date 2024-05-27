package com.example.ChatApplication.Entities;

public class HandshakeRequest {
    private String appID;
    private String chatSessionToken;
    public HandshakeRequest(){}
    public HandshakeRequest(String appID){
        this.appID=appID;
        this.chatSessionToken=null;
    }
    public HandshakeRequest(String appID,String chatSessionToken){
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
