package com.example.ChatApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

// Thread Pool to execute different type of services
@Configuration
@EnableAsync
public class AppConfig {
    
    // Thread pool for chat service
    @Bean("asyncChatServiceThread")
    public Executor asyncChatServiceExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(150);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setThreadNamePrefix("AsyncChatServiceThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    // Thread pool for token service
    @Bean("asyncTokenServiceThread")
    public Executor asyncTokenServiceExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncTokenServiceThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    // Thread pool for user service
    @Bean("asyncUserServiceThread")
    public Executor asyncUserServiceExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncUserServiceThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    // Thread pool for message service
    @Bean("asyncUserMessageThread")
    public Executor asyncMessageServiceExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncMessageServiceThread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
