package com.example.ChatApplication.RepoObjects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;


@Entity
public class TempChatConversation extends BasicChatConversation{
    private @Id @GeneratedValue Long conversationID;
    String appID;
    Long userID;
    public TempChatConversation(){
    }
    public TempChatConversation(String appID,Long userID){
        this.appID = appID;
        this.userID = userID;
    }
    public Long getConversationID(){
        return this.conversationID;
    }
    public String getAppID(){
        return this.appID;
    }
    public Long getUserID(){
        return this.userID;
    }
    public void setConversationID(Long id) {
        this.conversationID = id;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof TempChatConversation))
            return false;
        TempChatConversation tempChatConversation= (TempChatConversation) o;
        return Objects.equals(this.conversationID,tempChatConversation.conversationID) && Objects.equals(this.appID,tempChatConversation.appID) && Objects.equals(this.userID,tempChatConversation.userID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.conversationID, this.appID, this.userID);
    }
    @Override
    public String toString() {
        return "TempChatConversation{" + "id=" + this.conversationID + ", appId='" + this.appID + '\'' + ", userID='" + this.userID + '\'' + '}';
    }
}
