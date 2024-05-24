package com.example.ChatApplication.RepoObjects;

import jakarta.persistence.Id;

public class BasicChatConversation {
    private @Id Long conversationID;
    String appID;
    Long userID;
}
