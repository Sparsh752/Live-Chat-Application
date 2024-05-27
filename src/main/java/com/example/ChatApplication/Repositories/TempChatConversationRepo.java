package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.TempChatConversation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TempChatConversationRepo extends MongoRepository<TempChatConversation, String> {

}
