package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Entities.ChatUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatUserRepo extends MongoRepository<ChatUser, String> {
}
