package com.example.ChatApplication.RepoObjects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ChatConversation extends BasicChatConversation {
    private @Id Long conversationID;
    String appID;
    Long userID;
    Boolean isdelete;
    public ChatConversation(){
        this.isdelete=false;
    }
    public ChatConversation(Long conversationID,String appID,Long userID){
        this.conversationID=conversationID;
        this.appID = appID;
        this.userID = userID;
        this.isdelete=false;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public Long getUserID() {
        return userID;
    }

    public String getAppID() {
        return appID;
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
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof ChatConversation))
            return false;
        ChatConversation chatConversation= (ChatConversation) o;
        return Objects.equals(this.conversationID,chatConversation.conversationID) && Objects.equals(this.appID,chatConversation.appID) && Objects.equals(this.userID,chatConversation.userID);
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
