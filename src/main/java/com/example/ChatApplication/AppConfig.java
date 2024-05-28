package com.example.ChatApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AppConfig {

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
