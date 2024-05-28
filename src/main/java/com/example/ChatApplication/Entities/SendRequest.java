package com.example.ChatApplication.Entities;

// This class is used to represent the send request
public class SendRequest {
    String conversationID;
    String text;
    String chatSessionToken;

    SendRequest(){}

    SendRequest(String conversationID,String text,String chatSessionToken){
        this.chatSessionToken=chatSessionToken;
        this.text=text;
        this.conversationID=conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setChatSessionToken(String chatSessionToken) {
        this.chatSessionToken = chatSessionToken;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChatSessionToken() {
        return chatSessionToken;
    }

    public String getText() {
        return text;
    }
}
