package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.RepoObjects.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepo extends JpaRepository<ChatUser, Long> {
}
