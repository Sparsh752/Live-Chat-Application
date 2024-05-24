package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.RepoObjects.ChatConversation;
import com.example.ChatApplication.RepoObjects.TempChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatConversationRepo extends JpaRepository<ChatConversation, Long> {

}