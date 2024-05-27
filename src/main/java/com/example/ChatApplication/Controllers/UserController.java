package com.example.ChatApplication.Controllers;

import com.example.ChatApplication.Entities.ChatTokenClaims;
import com.example.ChatApplication.Entities.HandshakeRequest;
import com.example.ChatApplication.Entities.HandshakeResponse;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Exceptions.MissingException;
import com.example.ChatApplication.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    public UserController(){}

    @PostMapping("/handshake")
    HandshakeResponse handShake(@RequestBody HandshakeRequest handshakeRequest){
        if(handshakeRequest.getAppID()==null){
            throw new MissingException("App ID");
        }
        if(handshakeRequest.getChatSessionToken()==null){
            ChatTokenClaims chatTokenClaims=userServices.CreateNewUser(handshakeRequest.getAppID());
            return new HandshakeResponse(handshakeRequest.getAppID(), userServices.encode(chatTokenClaims));
        }else{
            String chatSessionToken=handshakeRequest.getChatSessionToken();
            ChatTokenClaims chatTokenClaims=userServices.decode(chatSessionToken);
            if(chatTokenClaims.getChatUserId()==null){
                throw new InvalidException("Chat Session Token");
            }
            if(chatTokenClaims.getAppId()==null){
                throw new InvalidException("Chat Session Token");
            }
            if(userServices.ValidateUser(chatTokenClaims.getChatUserId())){
                return new HandshakeResponse(handshakeRequest.getAppID(),chatSessionToken);
            }else{
                throw new InvalidException("User");
            }
        }
    }
}
