package com.delivery.order_service.webclient;

import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient webClient;

    public Mono<CommonResponse<FindUserByNameResponse>> getUserByUsernameClient(String token, String username) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("user-service/info")
                        .queryParam("username", "tom")
                        .build())
                .header("Authorization", token)
                .retrieve()
                //.bodyToMono(CommonResponse.class)
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<FindUserByNameResponse>>() {})
                .map(response -> response);
    }
}
