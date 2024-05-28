package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.TempChatConversation;
import org.springframework.data.mongodb.repository.MongoRepository;

// This interface is used to represent the temporary chat conversation repository   
public interface TempChatConversationRepo extends MongoRepository<TempChatConversation, String> {

}
