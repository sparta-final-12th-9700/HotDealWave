package com.sparta.hotdeal.product.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100); // 최소 쓰레드 수 (JMeter 쓰레드 수와 동일하게 설정)
        executor.setMaxPoolSize(200); // 최대 쓰레드 수 (여유롭게 설정)
        executor.setQueueCapacity(500); // 대기열 크기
        executor.setThreadNamePrefix("AsyncExecutor-"); // 쓰레드 이름 접두사
        executor.initialize();
        return executor;
    }
}