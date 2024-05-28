package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.*;
import com.example.ChatApplication.Exceptions.ConversationDeletedException;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Exceptions.UserAccessDeniedException;
import com.example.ChatApplication.Repositories.ChatConversationRepo;
import com.example.ChatApplication.Repositories.TempChatConversationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatServices {

    @Autowired
    private TempChatConversationRepo tempChatConversationRepo;

    @Autowired
    private ChatConversationRepo chatConversationRepo;

    @Autowired
    private MessageServices messageServices;

    public ChatServices(){}

    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<String> CreateNewTempChat(String appID, String userID){
        return CompletableFuture.completedFuture(tempChatConversationRepo.save(new TempChatConversation(appID,userID)).getConversationID());
    }

    @Transactional @Async("asyncChatServiceThread")
    public synchronized CompletableFuture<SendResponse> SaveChat(String conversationID, String text, String userID, String appID){
        Optional<ChatConversation> chatConversationRes = chatConversationRepo.findById(conversationID);
        if(chatConversationRes.isPresent()){
            ChatConversation chatConversation=chatConversationRes.get();
            if(!chatConversation.getAppID().equals(appID) || !chatConversation.getUserID().equals(userID)){
                throw new InvalidException("Conversation ID");
            }
            if(chatConversation.getIsdelete()){
                throw new ConversationDeletedException();
            }else{
                return messageServices.SaveMessage(text,conversationID)
                        .thenApply(chatMessage -> new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID()));
            }
        }else{
            Optional<TempChatConversation> tempChatConversationRes = tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationRes.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationRes.get();
                if(!tempChatConversation.getAppID().equals(appID) || !tempChatConversation.getUserID().equals(userID) ){
                    throw new InvalidException("Conversation ID");
                }
                if(!tempChatConversation.getUserID().equals(userID)){
                    throw new UserAccessDeniedException();
                }
                chatConversationRepo.save(new ChatConversation(conversationID,tempChatConversation.getAppID(),tempChatConversation.getUserID()));
                tempChatConversationRepo.deleteById(conversationID);
                return messageServices.SaveMessage(text,conversationID)
                        .thenApply(chatMessage -> new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID()));
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }

    @Transactional
    public synchronized boolean ValidateConversationID(String conversationID){
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            return true;
        }else{
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                return true;
            }else{
                return false;
            }
        }
    }



    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<List<ChatMessage>> fetchMessagesByConversationID(String conversationID){
        if (ValidateConversationID(conversationID)) {
            return messageServices.GetMessagesByConversationID(conversationID);
        }else {
            throw new InvalidException("Conversation ID");
        }
    }

    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<CompletableFuture<ChatMessage>> fetchMessageByMessageID(String messageID, String conversationID){
        if (ValidateConversationID(conversationID)) {
            return messageServices.ValidateMessageID(conversationID,messageID)
                    .thenApply(validated -> messageServices.GetMessage(messageID));
        }else{
            throw new InvalidException("Conversation ID");
        }

    }

    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<BasicChatConversation> fetchConversationByConversationID(String conversationID){
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            return CompletableFuture.completedFuture(chatConversationOptional.get());
        }else{
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                return CompletableFuture.completedFuture(tempChatConversationOptional.get());
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }
}
