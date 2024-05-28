package com.example.ChatApplication.Entities;

import java.time.Instant;

// This class is used to represent the new response
public class NewResponse {
    String conversationID;
    String appID;
    String userID;
    String welcomeMessage;
    Long creationTime;
    Long expirationTime;

    public NewResponse() {
        this.welcomeMessage = "Welcome to Sprinklr Chat!";
        this.creationTime = Instant.now().toEpochMilli();
        this.expirationTime = this.creationTime + 60000;
    }

    public NewResponse(String conversationID, String appID, String userID) {
        this.appID = appID;
        this.conversationID = conversationID;
        this.userID = userID;
        this.welcomeMessage = "Welcome to Sprinklr Chat!";
        this.creationTime = Instant.now().toEpochMilli();
        this.expirationTime = this.creationTime + 60000;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getAppID() {
        return appID;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getUserID() {
        return userID;
    }
}
