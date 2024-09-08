package com.delivery.order_service.service;

import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.feign.UserServiceFeignClient;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.webclient.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {
    //private final OrdersRepository orderRepository;
    private final UserServiceFeignClient userServiceFeignClient;
    private final WebClient webClient;
    private final UserServiceClient userServiceClient;


    public CreateOrderResponse createOrder() {
        CommonResponse<FindUserByNameResponse> feignResponse = userServiceFeignClient.getUserByUsername("tom");
        System.out.println("user -> "+ feignResponse.data());
        System.out.println("user.toString -> "+ feignResponse.data().toString());
        System.out.println("feign user success -> " + feignResponse.data().getName());
        return CreateOrderResponse.builder()
                .orderId(1L)
                .build();
    }

    public Mono<CommonResponse<FindUserByNameResponse>> createOrderWebClient() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        return userServiceClient.getUserByUsernameClient(authorizationHeader, "tom");
    }
}
