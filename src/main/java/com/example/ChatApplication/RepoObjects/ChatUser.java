package com.example.ChatApplication.RepoObjects;

import com.example.ChatApplication.Repositories.ChatUserRepo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ChatUser {
    private @Id @GeneratedValue Long userID;
    String userType;
    public ChatUser(){
    }
    public ChatUser(String userType){
        this.userType=userType;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getUserID() {
        return userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof ChatUser))
            return false;
        ChatUser chatUser=(ChatUser) o;
        return Objects.equals(chatUser.userID,this.userID) && Objects.equals(chatUser.userType,this.userType);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.userID,this.userType);
    }
    @Override
    public String toString() {
        return "ChatUser{" + "id=" + this.userID + ", userType="+this.userType +'}';
    }
}
