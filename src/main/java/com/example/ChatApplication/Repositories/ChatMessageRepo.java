package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// This interface is used to represent the chat message repository
public interface ChatMessageRepo extends MongoRepository<ChatMessage,String> {
    List<ChatMessage> findByConversationID(String conversationID);
}
