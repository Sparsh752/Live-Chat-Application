package com.example.ChatApplication.RepoObjects;

public class SendReturnObject {
    private Long messageID;
    Long conversationID;
    Long userID;
    String text;
    Long creationTime;
    public SendReturnObject(){}
    public SendReturnObject(Long conversationID, Long userID, String text, Long creationTime,Long messageID){
        this.conversationID=conversationID;
        this.userID=userID;
        this.text=text;
        this.creationTime=creationTime;
        this.messageID=messageID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setConversationID(Long conversationID) {
        this.conversationID = conversationID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getConversationID() {
        return conversationID;
    }

    public Long getMessageID() {
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
