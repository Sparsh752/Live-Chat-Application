package com.example.ChatApplication.Entities;

import org.springframework.data.annotation.Id;

import java.util.Objects;

// This class is used to represent a permanent chat conversation
public class ChatConversation extends BasicChatConversation {
    private @Id String conversationID;
    String appID;
    String userID;
    Boolean isdelete;

    public ChatConversation() {
        this.isdelete = false;
    }

    public ChatConversation(String conversationID, String appID, String userID) {
        this.conversationID = conversationID;
        this.appID = appID;
        this.userID = userID;
        this.isdelete = false;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAppID() {
        return appID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof ChatConversation))
            return false;
        ChatConversation chatConversation = (ChatConversation) o;
        return Objects.equals(this.conversationID, chatConversation.conversationID) && Objects.equals(this.appID, chatConversation.appID) && Objects.equals(this.userID, chatConversation.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.conversationID, this.appID, this.userID);
    }

    @Override
    public String toString() {
        return "ChatConversation{" + "id=" + this.conversationID + ", appId='" + this.appID + '\'' + ", userID='" + this.userID + '\'' + '}';
    }
}
