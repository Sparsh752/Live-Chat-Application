package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.ChatMessage;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Repositories.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageServices {

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Transactional @Async("asyncUserMessageThread")
    public CompletableFuture<ChatMessage> SaveMessage(String text, String conversationID){
        return CompletableFuture.completedFuture(chatMessageRepo.save(new ChatMessage(text,conversationID)));
    }
    @Transactional @Async("asyncUserMessageThread")
    public synchronized CompletableFuture<Boolean> ValidateMessageID(String conversationID, String messageID){
        Optional<ChatMessage> chatMessageOptional=chatMessageRepo.findById(messageID);
        if(chatMessageOptional.isPresent()){
            if(chatMessageOptional.get().getConversationID().equals(conversationID)){
                return CompletableFuture.completedFuture(true);
            }else{
                throw new InvalidException("Message ID");
            }
        }else {
            throw new InvalidException("Message ID");
        }
    }
    @Transactional @Async("asyncUserMessageThread")
    public CompletableFuture<ChatMessage> GetMessage(String messageID){
        return CompletableFuture.completedFuture(chatMessageRepo.findById(messageID).get());
    }
    @Transactional @Async("asyncUserMessageThread")
    public CompletableFuture<List<ChatMessage>> GetMessagesByConversationID(String conversationID){
        return CompletableFuture.completedFuture(chatMessageRepo.findByConversationID(conversationID));
    }
}
