package com.example.ChatApplication.Controllers;

import com.example.ChatApplication.Entities.*;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Exceptions.MissingException;
import com.example.ChatApplication.Services.ChatServices;
import com.example.ChatApplication.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatServices chatServices;

    @Autowired
    private UserServices userServices;

    public ChatController(){}

    @PostMapping("/new")
    NewResponse newConversation(@RequestBody NewRequest newRequest){
        if(newRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }
        ChatTokenClaims chatTokenClaims=userServices.decode(newRequest.getChatSessionToken());
        if(chatTokenClaims.getChatUserId()==null){
            throw new InvalidException("Chat Session Token");
        }
        if(chatTokenClaims.getAppId()==null){
            throw new InvalidException("Chat Session Token");
        }
        if(userServices.ValidateUser(chatTokenClaims.getChatUserId())){
            String conversationID = chatServices.CreateNewTempChat(chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId());
            return new NewResponse(conversationID,chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId());
        }else{
            throw new InvalidException("User");
        }
    }

    @PostMapping("/send")
    SendResponse newMessage(@RequestBody SendRequest sendRequest){
        if(sendRequest.getConversationID()==null){
            throw new MissingException("Conversation ID");
        }
        if(sendRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }
        if(sendRequest.getText()==null){
            throw new MissingException("Text");
        }
        ChatTokenClaims chatTokenClaims=userServices.decode(sendRequest.getChatSessionToken());
        if(chatTokenClaims.getChatUserId()==null){
            throw new InvalidException("Chat Session Token");
        }
        if(chatTokenClaims.getAppId()==null){
            throw new InvalidException("Chat Session Token");
        }
        if(userServices.ValidateUser(chatTokenClaims.getChatUserId())){
            if(chatServices.ValidateChatConversation(sendRequest.getConversationID(),chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId())){
                return chatServices.SaveChat(sendRequest.getConversationID(),sendRequest.getText(),chatTokenClaims.getChatUserId());
            }else{
                throw new InvalidException("Conversation ID");
            }
        }else{
            throw new InvalidException("User");
        }
    }

    @GetMapping("/messages/{conversationID}")
    List<ChatMessage> listMessages(@PathVariable String conversationID) {
        if (chatServices.ValidateConversationID(conversationID)) {
            return chatServices.fetchMessagesByConversationID(conversationID);
        } else {
            throw new InvalidException("Conversation ID");
        }
    }

    @GetMapping("messages/{conversationID}/{messageID}")
    ChatMessage listOneMessage(@PathVariable String conversationID, @PathVariable String messageID){
        if (chatServices.ValidateConversationID(conversationID)) {
            if(chatServices.ValidateMessageID(conversationID,messageID)){
                return chatServices.fetchMessageByMessageID(messageID);
            }else{
                throw new InvalidException("Message ID");
            }
        } else {
            throw new InvalidException("Conversation ID");
        }
    }

    @GetMapping("conversation/{conversationID}")
    BasicChatConversation fetchConversation(@PathVariable String conversationID){
        if(chatServices.ValidateConversationID(conversationID)){
            return chatServices.fetchConversationByConversationID(conversationID);
        }else {
            throw new InvalidException("Conversation ID");
        }
    }
}
