package com.example.ChatApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ChatApplication {

    public static void main(String[] args) {
        System.out.println("The pid of current process: " + ProcessHandle.current().pid());
        SpringApplication.run(ChatApplication.class, args);
    }

}
