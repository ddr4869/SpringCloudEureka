package com.delivery.api_gateway.Filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreGatewayFilter extends AbstractGatewayFilterFactory<PreGatewayFilter.Config> {

    public PreGatewayFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("request information : {}, {}", request.getHeaders(), request.getURI());

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();

            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    @Getter
    @Setter
    public static class Config {
        // 필터와 관련된 설정값을 추가할 수 있음
    }
}
