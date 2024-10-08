package com.delivery.api_gateway.filter;

import com.delivery.api_gateway.authentication.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationGatewayFilter extends
            AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilter.Config> {

    @Value("${jwt.exclude}")
    public List<String> excludePathList;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationGatewayFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                // 토큰 검증 제외 경로 설정
                String path = exchange.getRequest().getURI().getPath();
                PathMatcher pathMatcher = new AntPathMatcher();
                for (String excludePath : excludePathList) {
                    if (pathMatcher.match(excludePath, path)) {
                        return chain.filter(exchange);
                    }
                }

                String token = jwtUtil.resolveToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
                if (token != null && jwtUtil.validateToken(token)) {
                    String username = jwtUtil.getClaimsUsername(token);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Name", username)  // 하위 서비스에 유저 이름 전달
                            .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                } else {
                    log.warn("token -> {}", token);
                    log.info("*** Invalid token ***");
                    //return Mono.error(new RuntimeException("Invalid JWT token"));
                    return jwtErrResponse(exchange);
                    //return Mono.empty();
                }
            } catch (Exception e) {
                // handler exception
                log.info("*** Jwt Filter Error ***");
                return Mono.error(new RuntimeException("Error"));
            }
        };


    }

    @Getter
    @Setter
    public static class Config {
        // 필터와 관련된 설정값을 추가할 수 있음
    }

    private Mono<Void> jwtErrResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

        String errorMessage = "{\"error\": \"Unauthorized\", \"message\": \"Invalid JWT token\"}";
        byte[] bytes = errorMessage.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));

    }
}