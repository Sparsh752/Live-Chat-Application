package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.ChatTokenClaims;
import com.example.ChatApplication.Entities.ChatUser;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Repositories.ChatUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServices {

    @Autowired
    private ChatUserRepo chatUserRepo;

    // method to validate the user
    @Async("asyncUserServiceThread")
    public CompletableFuture<Boolean> ValidateUser(String userID) {
        Optional<ChatUser> chatUser = chatUserRepo.findById(userID);
        if (chatUser.isPresent()) {
            return CompletableFuture.completedFuture(true);
        }
        throw new InvalidException("User");
    }

    // method to create a new user
    @Async("asyncUserServiceThread")
    public CompletableFuture<ChatTokenClaims> CreateNewUser(String appID) {
        String userID = chatUserRepo.save(new ChatUser("ANONYMOUS", appID)).getUserID();
        return CompletableFuture.completedFuture(new ChatTokenClaims(userID, appID));
    }

}
