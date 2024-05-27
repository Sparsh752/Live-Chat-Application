package com.example.ChatApplication.Controllers;

import com.example.ChatApplication.Entities.*;
import com.example.ChatApplication.Exceptions.MissingException;
import com.example.ChatApplication.Services.ChatServices;
import com.example.ChatApplication.Services.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ChatController {

    @Autowired
    private ChatServices chatServices;

    @Autowired
    private TokenServices tokenServices;

    public ChatController(){}

    @PostMapping("/new")
    CompletableFuture<CompletableFuture<NewResponse>> newConversation(@RequestBody NewRequest newRequest){
        if(newRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }
        return tokenServices.ValidateChatSessionToken(newRequest.getChatSessionToken())
                .thenApply(chatTokenClaims -> {
                        return chatServices.CreateNewTempChat(chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId())
                                .thenApply(conversationID -> new NewResponse(conversationID,chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId()));
                });

    }

    @PostMapping("/send")
    CompletableFuture<CompletableFuture<SendResponse>> newMessage(@RequestBody SendRequest sendRequest){
        if(sendRequest.getConversationID()==null){
            throw new MissingException("Conversation ID");
        }
        if(sendRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }
        if(sendRequest.getText()==null){
            throw new MissingException("Text");
        }
        return tokenServices.ValidateChatSessionToken(sendRequest.getChatSessionToken())
                .thenApply(chatTokenClaims -> chatServices.SaveChat(sendRequest.getConversationID(),sendRequest.getText(),chatTokenClaims.getChatUserId(),chatTokenClaims.getAppId())
                );
    }

    @GetMapping("/messages/{conversationID}")
    CompletableFuture<List<ChatMessage>> listMessages(@PathVariable String conversationID) {
        return chatServices.fetchMessagesByConversationID(conversationID);
    }

    @GetMapping("messages/{conversationID}/{messageID}")
    CompletableFuture<CompletableFuture<ChatMessage>> listOneMessage(@PathVariable String conversationID, @PathVariable String messageID){
        return chatServices.fetchMessageByMessageID(messageID,conversationID);
    }

    @GetMapping("conversation/{conversationID}")
    CompletableFuture<BasicChatConversation> fetchConversation(@PathVariable String conversationID){
        return chatServices.fetchConversationByConversationID(conversationID);
    }
}
