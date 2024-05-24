package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.Exceptions.*;
import com.example.ChatApplication.RepoObjects.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DatabaseController {

    private static TempChatConversationRepo tempChatConversationRepo = null;
    private final ChatUserRepo chatUserRepo;
    private final ChatConversationRepo chatConversationRepo;
    private final ChatMessageRepo chatMessageRepo;

    DatabaseController(TempChatConversationRepo tempChatConversationRepo, ChatUserRepo chatUserRepo,ChatConversationRepo chatConversationRepo,ChatMessageRepo chatMessageRepo){
        this.tempChatConversationRepo=tempChatConversationRepo;
        this.chatUserRepo = chatUserRepo;
        this.chatConversationRepo=chatConversationRepo;
        this.chatMessageRepo=chatMessageRepo;
    }

    static synchronized void DeleteTempChatConversation(Long conversationID){
        if(tempChatConversationRepo.findById(conversationID).isPresent()){
            System.out.println("TempChatConversation is Deleted with conversationID " + conversationID);
            tempChatConversationRepo.deleteById(conversationID);
        }
    }

    @GetMapping("/register")
    ChatUser getUser(){
        return chatUserRepo.save(new ChatUser("REGISTERED"));
    }

    @PostMapping("/new")
    NewReturnObject newConversation(@RequestBody TempChatConversation tempChatConversation){
        if(tempChatConversation.getAppID()==null){
            throw new MissingAppIdException();
        }
        if(tempChatConversation.getUserID()==null){
            Long userID = chatUserRepo.save(new ChatUser("ANONYMOUS")).getUserID();
            tempChatConversation.setUserID(userID);
        }
        TempChatConversation tempChatConversation_db= tempChatConversationRepo.save(tempChatConversation);
        Thread thr=new Thread(new TTLRunner(tempChatConversation_db.getConversationID()));
        thr.start();
        ChatUser user=chatUserRepo.findById(tempChatConversation_db.getUserID()).get();
        return new NewReturnObject(tempChatConversation_db.getConversationID(),tempChatConversation_db.getAppID(),tempChatConversation_db.getUserID(),user.getUserType());
    }

    @PostMapping("/send")
    SendReturnObject carryConversation(@RequestBody SendChatObj chatPayload){
        Optional<ChatConversation> chatConversationRes = chatConversationRepo.findById(chatPayload.getConversationID());
        if(chatConversationRes.isPresent()){
            ChatConversation chatConversation=chatConversationRes.get();
            if(chatPayload.getUserID()!=chatConversation.getUserID()){
                throw new UserAccessDeniedException();
            }
            if(chatConversation.getIsdelete()){
                throw new ConversationDeletedException();
            }else{
                ChatMessage chatMessage = chatMessageRepo.save(new ChatMessage(chatPayload.getText(),chatPayload.getConversationID()));
                return new SendReturnObject(chatPayload.getConversationID(),chatPayload.getUserID(),chatPayload.getText(),chatMessage.getCreationTime(),chatMessage.getMessageID());
            }
        }else{
            Optional<TempChatConversation> tempChatConversationRes = tempChatConversationRepo.findById(chatPayload.getConversationID());
            if(tempChatConversationRes.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationRes.get();
                if(tempChatConversation.getUserID()!=chatPayload.getUserID()){
                    throw new UserAccessDeniedException();
                }
                chatConversationRepo.save(new ChatConversation(chatPayload.getConversationID(),tempChatConversation.getAppID(),tempChatConversation.getUserID()));
                tempChatConversationRepo.deleteById(chatPayload.getConversationID());
                ChatMessage chatMessage = chatMessageRepo.save(new ChatMessage(chatPayload.getText(),chatPayload.getConversationID()));
                return new SendReturnObject(chatPayload.getConversationID(),chatPayload.getUserID(),chatPayload.getText(),chatMessage.getCreationTime(),chatMessage.getMessageID());
            }else{
                throw new InvalidConversationIdException();
            }
        }
    }

    @GetMapping("/messages/{id}")
    CollectionModel<EntityModel<ChatMessage>> listMessages(@PathVariable Long id){
        List<EntityModel<ChatMessage>> chatMessageList= chatMessageRepo.findByConversationID(id).stream()
                .map(ChatMessage -> EntityModel.of(ChatMessage,
                        linkTo(methodOn(DatabaseController.class).listOneMessage(id,ChatMessage.getMessageID())).withSelfRel(),
                        linkTo(methodOn(DatabaseController.class).listMessages(id)).withRel("All Conversation Messages"),
                        linkTo(methodOn(DatabaseController.class).fetchConversation(id)).withRel("Conversation Entity")))
                .collect(Collectors.toList());
        return CollectionModel.of(chatMessageList,
                linkTo(methodOn(DatabaseController.class).listMessages(id)).withSelfRel(),
                linkTo(methodOn(DatabaseController.class).fetchConversation(id)).withRel("Conversation Entity"));
    }

    @GetMapping("messages/{conversationID}/{messageID}")
    EntityModel<ChatMessage> listOneMessage(@PathVariable Long conversationID, @PathVariable Long messageID){
        Optional<ChatMessage> chatMessageOptional=chatMessageRepo.findById(messageID);
        if(chatMessageOptional.isPresent()){
            ChatMessage chatMessage=chatMessageOptional.get();
            if(chatMessage.getConversationID()==conversationID){
                EntityModel<ChatMessage> chatMessageEntityModel=EntityModel.of(chatMessage,
                        linkTo(methodOn(DatabaseController.class).listOneMessage(conversationID,messageID)).withSelfRel(),
                        linkTo(methodOn(DatabaseController.class).listMessages(conversationID)).withRel("All Conversation Messages"),
                        linkTo(methodOn(DatabaseController.class).fetchConversation(conversationID)).withRel("Conversation Entity"));
                return chatMessageEntityModel;
            }else{
                throw new WrongMessageIDException();
            }
        }else{
            throw new WrongMessageIDException();
        }
    }

    @GetMapping("conversation/{conversationID}")
    EntityModel<BasicChatConversation> fetchConversation(@PathVariable Long conversationID){
        Optional<ChatConversation> chatConversationOptional=chatConversationRepo.findById(conversationID);
        if(chatConversationOptional.isPresent()){
            ChatConversation chatConversation=chatConversationOptional.get();
            EntityModel<BasicChatConversation> chatConversationEntityModel=EntityModel.of(chatConversation,
                    linkTo(methodOn(DatabaseController.class).fetchConversation(conversationID)).withSelfRel(),
                    linkTo(methodOn(DatabaseController.class).listMessages(conversationID)).withRel("All Conversation Messages"));
            return chatConversationEntityModel;
        }else{
            Optional<TempChatConversation> tempChatConversationOptional=tempChatConversationRepo.findById(conversationID);
            if (tempChatConversationOptional.isPresent()){
                TempChatConversation tempChatConversation=tempChatConversationOptional.get();
                EntityModel<BasicChatConversation> tempChatConversationEntityModel=EntityModel.of(tempChatConversation,
                        linkTo(methodOn(DatabaseController.class).fetchConversation(conversationID)).withSelfRel(),
                        linkTo(methodOn(DatabaseController.class).listMessages(conversationID)).withRel("All Conversation Messages"));
                return tempChatConversationEntityModel;
            }else{
                throw new InvalidConversationIdException();
            }
        }
    }
}
