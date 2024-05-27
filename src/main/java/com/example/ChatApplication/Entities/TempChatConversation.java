package com.example.ChatApplication.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
import java.util.Objects;

public class TempChatConversation extends BasicChatConversation{
    private @Id String conversationID;
    @Indexed(name = "TTL",expireAfterSeconds = 1)
    private Instant createdAt;
    private String appID;
    private String userID;

    public TempChatConversation(){
    }
    public TempChatConversation(String appID,String userID){
        this.appID = appID;
        this.userID = userID;
        this.createdAt = Instant.now();
    }
    public Instant getExpiredOn() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getConversationID(){
        return this.conversationID;
    }
    public String getAppID(){
        return this.appID;
    }
    public String getUserID(){
        return this.userID;
    }
    public void setConversationID(String id) {
        this.conversationID = id;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setUserID(String userID) {
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
