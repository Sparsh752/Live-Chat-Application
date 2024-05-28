package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

// This interface is used to represent the chat conversation repository
public interface ChatConversationRepo extends MongoRepository<ChatConversation, String> {

}