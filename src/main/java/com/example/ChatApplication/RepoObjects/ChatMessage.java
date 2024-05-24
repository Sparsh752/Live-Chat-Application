package com.example.ChatApplication.RepoObjects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Objects;

@Entity
public class ChatMessage {
    private @Id @GeneratedValue Long messageID;
    String text;
    Long conversationID;
    Long creationTime;
    public ChatMessage(){}
    public ChatMessage(String text,Long conversationID){
        this.conversationID=conversationID;
        this.text=text;
        creationTime= Instant.now().toEpochMilli();
    }

    public Long getMessageID() {
        return messageID;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public String getText() {
        return text;
    }

    public void setConversationID(Long conversationID) {
        this.conversationID = conversationID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof ChatMessage))
            return false;
        ChatMessage chatMessage=(ChatMessage) o;
        return Objects.equals(chatMessage.conversationID,this.conversationID) && Objects.equals(chatMessage.messageID,this.messageID) && Objects.equals(chatMessage.text,this.text) && Objects.equals(this.creationTime,chatMessage.creationTime);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.conversationID,this.messageID,this.text,this.creationTime);
    }
    @Override
    public String toString() {
        return "ChatMessage{" + "id=" + this.messageID + ", conversationID="+this.conversationID+", text="+this.text +", creationTime="+this.creationTime+'}';
    }
}
