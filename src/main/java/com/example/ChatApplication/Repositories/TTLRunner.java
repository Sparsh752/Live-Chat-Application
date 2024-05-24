package com.example.ChatApplication.Repositories;

public class TTLRunner implements Runnable{

    Long conversationID;

    public TTLRunner(Long conversationID){
        this.conversationID=conversationID;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        DatabaseController.DeleteTempChatConversation(conversationID);
    }
}
