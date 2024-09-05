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
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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
                System.out.println("start!!");
                // 토큰 검증 제외 경로 설정
                String path = exchange.getRequest().getURI().getPath();
                PathMatcher pathMatcher = new AntPathMatcher();
                System.out.println("path: " + path);
                for (String excludePath : excludePathList) {
                    System.out.println("excludePath: " + excludePath);
                    if (pathMatcher.match(excludePath, path)) {
                        System.out.println("exit !! ");
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
                    log.info("*** Invalid token ***");
                    return Mono.error(new RuntimeException("Invalid JWT token"));
                    //jwtErrResponse(response, CommonErrorCode.INVALID_TOKEN);
                }
            } catch (Exception e) {
                // handler exception
                log.info("*** Jwt Filter Error ***");
                e.printStackTrace();
                return Mono.error(new RuntimeException("Error"));
            }
        };
    }

    @Getter
    @Setter
    public static class Config {
        // 필터와 관련된 설정값을 추가할 수 있음
    }
}