package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.RepoObjects.TempChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;

interface TempChatConversationRepo extends JpaRepository<TempChatConversation, Long> {

}
