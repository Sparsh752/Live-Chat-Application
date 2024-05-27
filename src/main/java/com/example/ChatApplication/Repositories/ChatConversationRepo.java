package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatConversationRepo extends MongoRepository<ChatConversation, String> {

}