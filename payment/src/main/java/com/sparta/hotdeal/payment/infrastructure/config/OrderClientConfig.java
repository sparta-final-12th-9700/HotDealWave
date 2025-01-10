package com.sparta.hotdeal.payment.infrastructure.config;

import com.sparta.hotdeal.payment.infrastructure.exception.decoder.OrderClientErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class OrderClientConfig {
    @Value("${admin.user-id}")
    private String userId;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.role}")
    private String role;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-User-UserId", userId);
            requestTemplate.header("X-User-Email", email);
            requestTemplate.header("X-User-Role", role);
        };
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new OrderClientErrorDecoder();
    }
}
