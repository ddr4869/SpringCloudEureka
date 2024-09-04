package com.example.first_service.service;

import com.example.first_service.feign.FeignCallClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Service
public class FirstService {
    private final FeignCallClient feignCallClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public Object getFeignCall() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Dto dto = new Dto();
        dto.data = circuitBreaker.run(
                feignCallClient::getException,
                throwable -> Collections.singletonList("fallback"));
        return dto;
    }

    @Getter
    @Setter
    class Dto {
        private Object data;
    }
}
