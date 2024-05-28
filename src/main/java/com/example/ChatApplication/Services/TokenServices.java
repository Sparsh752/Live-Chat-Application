package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.ChatTokenClaims;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Service
public class TokenServices {

    @Autowired
    private UserServices userServices;

    // method to encode the chatTokenClaims
    public String encode(ChatTokenClaims chatTokenClaims){
        String chatTokenClaims_str=new Gson().toJson(chatTokenClaims);
        System.out.println(chatTokenClaims_str);
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String payload=new String(encoder.encode(chatTokenClaims_str.getBytes()));
        String header="eyJhbGciOiJSUzI1NiJ9";
        String chatSessionToken=header+"."+payload;
        return chatSessionToken;
    }

    // method to decode the chatSessionToken
    public ChatTokenClaims decode(String chatSessionToken){
        String[] chunks = chatSessionToken.split("\\.");
        String header,payload;
        try {
            Base64.Decoder decoder = Base64.getUrlDecoder();
            header = new String(decoder.decode(chunks[0]));
            payload = new String(decoder.decode(chunks[1]));
        }catch (Exception e){
            throw new InvalidException("Chat Session Token");
        }
        Gson g = new Gson();
        ChatTokenClaims chatTokenClaims = g.fromJson(payload, ChatTokenClaims.class);
        return chatTokenClaims;
    }

    @Async("asyncTokenServiceThread")
    public CompletableFuture<ChatTokenClaims> ValidateChatSessionToken(String chatSessionToken) {

        // decode the chatSessionToken
        ChatTokenClaims chatTokenClaims = decode(chatSessionToken);

        // check if the chatTokenClaims are valid
        if (chatTokenClaims.getChatUserId() == null) {
            throw new InvalidException("Chat Session Token");
        }
        if (chatTokenClaims.getAppId() == null) {
            throw new InvalidException("Chat Session Token");
        }
        return userServices.ValidateUser(chatTokenClaims.getChatUserId())
                .thenApply(validated-> chatTokenClaims);
    }
}
