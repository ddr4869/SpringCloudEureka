package com.delivery.order_service.webclient;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

//    public static WebClient addAuthorizationHeader(WebClient.Builder builder, String token) {
//        return builder
//                .filter(authorizationHeaderFilter(token))  // 필터 추가
//                .build();
//    }
//
//    private ExchangeFilterFunction authorizationHeaderFilter(String token) {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
//                    .header("Authorization", "Bearer " + token)  // Bearer 토큰 추가
//                    .build();
//            return Mono.just(authorizedRequest);
//        });
//    }
}