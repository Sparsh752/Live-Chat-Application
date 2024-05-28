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

    // method to create a new temporary chat
    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<String> CreateNewTempChat(String appID, String userID){
        return CompletableFuture.completedFuture(tempChatConversationRepo.save(new TempChatConversation(appID,userID)).getConversationID());
    }

    // method to create a new chat
    @Transactional @Async("asyncChatServiceThread")
    public synchronized CompletableFuture<SendResponse> SaveChat(String conversationID, String text, String userID, String appID){
        
        // check if the conversationID is present in the chatConversationRepo
        Optional<ChatConversation> chatConversationRes = chatConversationRepo.findById(conversationID);
        if(chatConversationRes.isPresent()){
            ChatConversation chatConversation=chatConversationRes.get();

            // check if the appID and userID are valid
            if(!chatConversation.getAppID().equals(appID) || !chatConversation.getUserID().equals(userID)){
                throw new InvalidException("Conversation ID");
            }
            if(chatConversation.getIsdelete()){
                throw new ConversationDeletedException();
            }else{

                // save the message and return the response
                return messageServices.SaveMessage(text,conversationID)
                        .thenApply(chatMessage -> new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID()));
            }
        }else{

            // check if the conversationID is present in the tempChatConversationRepo
            Optional<TempChatConversation> tempChatConversationRes = tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationRes.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationRes.get();

                // check if the appID and userID are valid
                if(!tempChatConversation.getAppID().equals(appID) || !tempChatConversation.getUserID().equals(userID) ){
                    throw new InvalidException("Conversation ID");
                }

                // delete conversation from tempChatConversationRepo and save it in chatConversationRepo
                chatConversationRepo.save(new ChatConversation(conversationID,tempChatConversation.getAppID(),tempChatConversation.getUserID()));
                tempChatConversationRepo.deleteById(conversationID);

                // save the message and return the response
                return messageServices.SaveMessage(text,conversationID)
                        .thenApply(chatMessage -> new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID()));
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }

    // method to validate a conversation ID
    @Transactional
    public synchronized boolean ValidateConversationID(String conversationID){
        // check if the conversationID is present in the chatConversationRepo
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            return true;
        }else{

            // check if the conversationID is present in the tempChatConversationRepo
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                return true;
            }else{
                return false;
            }
        }
    }

    // method to fetch messages by conversation ID
    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<List<ChatMessage>> fetchMessagesByConversationID(String conversationID){
        if (ValidateConversationID(conversationID)) {

            // return the messages
            return messageServices.GetMessagesByConversationID(conversationID);
        }else {
            throw new InvalidException("Conversation ID");
        }
    }

    // method to fetch a message by message ID
    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<CompletableFuture<ChatMessage>> fetchMessageByMessageID(String messageID, String conversationID){
        if (ValidateConversationID(conversationID)) {

            // validate the message ID and return the message
            return messageServices.ValidateMessageID(conversationID,messageID)
                    .thenApply(validated -> messageServices.GetMessage(messageID));
        }else{
            throw new InvalidException("Conversation ID");
        }

    }

    // method to fetch a conversation by conversation ID
    @Transactional @Async("asyncChatServiceThread")
    public CompletableFuture<BasicChatConversation> fetchConversationByConversationID(String conversationID){

        // check if the conversationID is present in the chatConversationRepo
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            return CompletableFuture.completedFuture(chatConversationOptional.get());
        }else{

            // check if the conversationID is present in the tempChatConversationRepo
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                return CompletableFuture.completedFuture(tempChatConversationOptional.get());
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }
}
