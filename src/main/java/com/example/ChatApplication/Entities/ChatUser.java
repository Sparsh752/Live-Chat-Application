package com.example.ChatApplication.Entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class ChatUser {
    private @Id String userID;
    private String userType;
    private String appID;
    public ChatUser(){
    }
    public ChatUser(String userType,String appID){
        this.userType=userType;
        this.appID=appID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserID() {
        return userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppID() {
        return appID;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof ChatUser))
            return false;
        ChatUser chatUser=(ChatUser) o;
        return Objects.equals(chatUser.userID,this.userID) && Objects.equals(chatUser.userType,this.userType) && Objects.equals(chatUser.appID,this.appID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.userID,this.userType,this.appID);
    }
    @Override
    public String toString() {
        return "ChatUser{" + "id=" + this.userID + ", userType="+this.userType +'}';
    }
}
