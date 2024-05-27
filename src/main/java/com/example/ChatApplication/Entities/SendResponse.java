package com.example.ChatApplication.Entities;

public class SendResponse {
    private String messageID;
    String conversationID;
    String userID;
    String text;
    Long creationTime;
    public SendResponse(){}
    public SendResponse(String conversationID, String userID, String text, Long creationTime, String messageID){
        this.conversationID=conversationID;
        this.userID=userID;
        this.text=text;
        this.creationTime=creationTime;
        this.messageID=messageID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getUserID() {
        return userID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getText() {
        return text;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getCreationTime() {
        return creationTime;
    }
}
