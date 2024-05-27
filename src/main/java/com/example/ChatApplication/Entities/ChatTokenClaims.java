package com.example.ChatApplication.Entities;

public class ChatTokenClaims {
    String chatUserId;
    String appId;
    ChatTokenClaims(){}
    public ChatTokenClaims(String chatUserId, String appId){
        this.appId=appId;
        this.chatUserId=chatUserId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getAppId() {
        return appId;
    }

    public String getChatUserId() {
        return chatUserId;
    }
}
