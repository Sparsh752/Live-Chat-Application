package com.example.ChatApplication.RepoObjects;

import java.time.Instant;

public class NewReturnObject {
    Long conversationID;
    String appID;
    Long userID;
    String userType;
    String welcomeMessage;
    Long creationTime;
    Long expirationTime;
    public NewReturnObject(){}
    public NewReturnObject(Long conversationID,String appID,Long userID,String userType){
        this.appID=appID;
        this.conversationID=conversationID;
        this.userID=userID;
        this.userType=userType;
        this.welcomeMessage="Welcome to Sprinklr Chat!";
        this.creationTime= Instant.now().toEpochMilli();
        this.expirationTime=this.creationTime+60000;
    }

    public String getUserType() {
        return userType;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public String getAppID() {
        return appID;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setConversationID(Long conversationID) {
        this.conversationID = conversationID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}

