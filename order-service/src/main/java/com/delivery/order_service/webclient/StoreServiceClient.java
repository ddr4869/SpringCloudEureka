package com.delivery.order_service.webclient;

import com.delivery.order_service.dto.FindStoreByNameResponse;
import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class StoreServiceClient {
    private final WebClient webClient;
    public Mono<CommonResponse<FindStoreByNameResponse>> getStoreByNameClient(String token, String storeName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("store-service/info")
                        .queryParam("name", storeName)
                        .build())
                .header("Authorization", token)
                .retrieve()
                //.bodyToMono(CommonResponse.class)
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<FindStoreByNameResponse>>() {})
                .map(response -> response);

    }
}
