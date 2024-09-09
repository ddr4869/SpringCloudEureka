package com.delivery.order_service.webclient;

import com.delivery.order_service.dto.FindMenuByNameResponse;
import com.delivery.order_service.dto.FindStoreByNameResponse;
import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MenuServiceClient {

    private final WebClient webClient;

    public Mono<CommonResponse<FindMenuByNameResponse>> getMenuServiceByNameClient(String token, String menuName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("menu-service/info")
                        .queryParam("name", menuName)
                        .build())
                .header("Authorization", token)
                .retrieve()
                //.bodyToMono(CommonResponse.class)
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<FindMenuByNameResponse>>() {})
                .map(response -> response);
    }






}
