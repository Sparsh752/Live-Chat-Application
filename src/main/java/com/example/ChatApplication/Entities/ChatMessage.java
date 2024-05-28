package com.example.ChatApplication.Entities;

import org.springframework.data.annotation.Id;
import java.time.Instant;
import java.util.Objects;

// This class is used to represent a chat message
public class ChatMessage {
    private @Id String messageID;
    private String text;
    private String conversationID;
    private Long creationTime;
    public ChatMessage(){}
    public ChatMessage(String text,String conversationID){
        this.conversationID=conversationID;
        this.text=text;
        creationTime= Instant.now().toEpochMilli();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getText() {
        return text;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setMessageID(String messageID) {
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
