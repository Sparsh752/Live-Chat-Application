package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatUser;
import org.springframework.data.mongodb.repository.MongoRepository;

// This interface is used to represent the chat user repository
public interface ChatUserRepo extends MongoRepository<ChatUser, String> {
}
