package com.example.ChatApplication.RepoObjects;

public class SendChatObj {
    private Long messageID;
    Long conversationID;
    Long userID;
    String text;
    SendChatObj(){}
    SendChatObj(Long conversationID,Long userID,String text){
        this.conversationID=conversationID;
        this.userID=userID;
        this.text=text;
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
}
