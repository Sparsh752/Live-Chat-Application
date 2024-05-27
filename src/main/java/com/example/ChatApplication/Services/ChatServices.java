package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.*;
import com.example.ChatApplication.Exceptions.ConversationDeletedException;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Exceptions.UserAccessDeniedException;
import com.example.ChatApplication.Repositories.ChatConversationRepo;
import com.example.ChatApplication.Repositories.ChatMessageRepo;
import com.example.ChatApplication.Repositories.TempChatConversationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServices {

    @Autowired
    private TempChatConversationRepo tempChatConversationRepo;

    @Autowired
    private ChatConversationRepo chatConversationRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;


    public ChatServices(){}

    public String CreateNewTempChat(String appID,String userID){
        return tempChatConversationRepo.save(new TempChatConversation(appID,userID)).getConversationID();
    }

    public boolean ValidateChatConversation(String conversationID,String appID,String userID){
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            ChatConversation chatConversation=chatConversationOptional.get();
            if(chatConversation.getAppID().equals(appID) && chatConversation.getUserID().equals(userID)){
                return true;
            }else{
                return false;
            }
        }else{
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationOptional.get();
                if(tempChatConversation.getAppID().equals(appID) && tempChatConversation.getUserID().equals(userID) ){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    public SendResponse SaveChat(String conversationID,String text,String userID){
        Optional<ChatConversation> chatConversationRes = chatConversationRepo.findById(conversationID);
        if(chatConversationRes.isPresent()){
            ChatConversation chatConversation=chatConversationRes.get();
            if(chatConversation.getIsdelete()){
                throw new ConversationDeletedException();
            }else{
                ChatMessage chatMessage = chatMessageRepo.save(new ChatMessage(text,conversationID));
                return new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID());
            }
        }else{
            Optional<TempChatConversation> tempChatConversationRes = tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationRes.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationRes.get();
                if(!tempChatConversation.getUserID().equals(userID)){
                    throw new UserAccessDeniedException();
                }
                chatConversationRepo.save(new ChatConversation(conversationID,tempChatConversation.getAppID(),tempChatConversation.getUserID()));
                tempChatConversationRepo.deleteById(conversationID);
                ChatMessage chatMessage = chatMessageRepo.save(new ChatMessage(text,conversationID));
                return new SendResponse(conversationID,userID,text,chatMessage.getCreationTime(),chatMessage.getMessageID());
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }
    public boolean ValidateConversationID(String conversationID){
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
    public boolean ValidateMessageID(String conversationID, String messageID){
        Optional<ChatMessage> chatMessageOptional=chatMessageRepo.findById(messageID);
        if(chatMessageOptional.isPresent()){
            if(chatMessageOptional.get().getConversationID().equals(conversationID)){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    public List<ChatMessage> fetchMessagesByConversationID(String conversationID){
        return chatMessageRepo.findByConversationID(conversationID);
    }

    public ChatMessage fetchMessageByMessageID(String messageID){
        return chatMessageRepo.findById(messageID).get();
    }

    public BasicChatConversation fetchConversationByConversationID(String conversationID){
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            return chatConversationOptional.get();
        }else{
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if(tempChatConversationOptional.isPresent()){
                return tempChatConversationOptional.get();
            }else{
                throw new InvalidException("Conversation ID");
            }
        }
    }
}
