package com.example.ChatApplication.Services;

import com.example.ChatApplication.Entities.ChatTokenClaims;
import com.example.ChatApplication.Entities.ChatUser;
import com.example.ChatApplication.Exceptions.InvalidException;
import com.example.ChatApplication.Repositories.ChatUserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private ChatUserRepo chatUserRepo;

    public boolean ValidateUser(String userID){
        Optional<ChatUser> chatUser = chatUserRepo.findById(userID);
        if(chatUser.isPresent()){
            return true;
        }else{
            return false;
        }
    }
    public ChatTokenClaims CreateNewUser(String appID){
        String userID=chatUserRepo.save(new ChatUser("ANONYMOUS",appID)).getUserID();
        System.out.println(userID);
        return new ChatTokenClaims(userID,appID);
    }
    public String encode(ChatTokenClaims chatTokenClaims){
        String chatTokenClaims_str=new Gson().toJson(chatTokenClaims);
        System.out.println(chatTokenClaims_str);
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String payload=new String(encoder.encode(chatTokenClaims_str.getBytes()));
        String header="eyJhbGciOiJSUzI1NiJ9";
        String chatSessionToken=header+"."+payload;
        return chatSessionToken;
    }
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
}
