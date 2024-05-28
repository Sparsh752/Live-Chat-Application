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

    // endpoint to create a new conversation
    @PostMapping("/new")
    CompletableFuture<CompletableFuture<NewResponse>> newConversation(@RequestBody NewRequest newRequest){

        // check if the chatSessionToken is missing
        if(newRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }

        // validate the chatSessionToken and then create a new conversation
        return tokenServices.ValidateChatSessionToken(newRequest.getChatSessionToken())
                .thenApply(chatTokenClaims -> {
                        return chatServices.CreateNewTempChat(chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId())
                                .thenApply(conversationID -> new NewResponse(conversationID,chatTokenClaims.getAppId(),chatTokenClaims.getChatUserId()));
                });

    }

    // endpoint to send a new message
    @PostMapping("/send")
    CompletableFuture<CompletableFuture<SendResponse>> newMessage(@RequestBody SendRequest sendRequest){

        // check if the conversationID is missing
        if(sendRequest.getConversationID()==null){
            throw new MissingException("Conversation ID");
        }

        // check if the chatSessionToken is missing
        if(sendRequest.getChatSessionToken()==null){
            throw new MissingException("Chat Session Token");
        }

        // check if the text is missing
        if(sendRequest.getText()==null){
            throw new MissingException("Text");
        }

        // validate the chatSessionToken and then save the message
        return tokenServices.ValidateChatSessionToken(sendRequest.getChatSessionToken())
                .thenApply(chatTokenClaims -> chatServices.SaveChat(sendRequest.getConversationID(),sendRequest.getText(),chatTokenClaims.getChatUserId(),chatTokenClaims.getAppId())
                );
    }

    // endpoint to list all messages in a conversation
    @GetMapping("/messages/{conversationID}")
    CompletableFuture<List<ChatMessage>> listMessages(@PathVariable String conversationID) {
        return chatServices.fetchMessagesByConversationID(conversationID);
    }

    // endpoint to list a single message in a conversation
    @GetMapping("messages/{conversationID}/{messageID}")
    CompletableFuture<CompletableFuture<ChatMessage>> listOneMessage(@PathVariable String conversationID, @PathVariable String messageID){
        return chatServices.fetchMessageByMessageID(messageID,conversationID);
    }

    // endpoint to get the conversation details
    @GetMapping("conversation/{conversationID}")
    CompletableFuture<BasicChatConversation> fetchConversation(@PathVariable String conversationID){
        return chatServices.fetchConversationByConversationID(conversationID);
    }
}
