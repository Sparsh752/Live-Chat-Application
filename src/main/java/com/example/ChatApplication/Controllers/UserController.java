package com.example.ChatApplication.Controllers;

import com.example.ChatApplication.Entities.HandshakeRequest;
import com.example.ChatApplication.Entities.HandshakeResponse;
import com.example.ChatApplication.Exceptions.MissingException;
import com.example.ChatApplication.Services.TokenServices;
import com.example.ChatApplication.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private TokenServices tokenServices;

    public UserController(){}

    @PostMapping("/handshake")
    CompletableFuture<HandshakeResponse> handShake(@RequestBody HandshakeRequest handshakeRequest){
        if(handshakeRequest.getAppID()==null){
            throw new MissingException("App ID");
        }
        if(handshakeRequest.getChatSessionToken()==null){
            return userServices.CreateNewUser(handshakeRequest.getAppID())
                    .thenApply(chatTokenClaims -> new HandshakeResponse(handshakeRequest.getAppID(), tokenServices.encode(chatTokenClaims)));
        }else{
            String chatSessionToken=handshakeRequest.getChatSessionToken();
            return tokenServices.ValidateChatSessionToken(chatSessionToken)
                    .thenApply(chatTokenClaims -> userServices.ValidateUser(chatTokenClaims.getChatUserId()))
                    .thenApply(validated-> new HandshakeResponse(handshakeRequest.getAppID(),chatSessionToken));
        }
    }
}
