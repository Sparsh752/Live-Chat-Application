package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.RepoObjects.ChatMessage;
import com.example.ChatApplication.RepoObjects.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByConversationID(Long conversationID);
}
