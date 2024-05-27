package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepo extends MongoRepository<ChatMessage,String> {
    List<ChatMessage> findByConversationID(String conversationID);
}
