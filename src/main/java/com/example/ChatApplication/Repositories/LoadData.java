package com.example.ChatApplication.Repositories;

import com.example.ChatApplication.RepoObjects.TempChatConversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadData {

    private static final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(TempChatConversationRepo repository){
        return args -> {
          log.info("" + repository.save(new TempChatConversation("samsung1122", 5L)));
          log.info("" + repository.save(new TempChatConversation("apple1122", 2L)));
        };
    }
}

