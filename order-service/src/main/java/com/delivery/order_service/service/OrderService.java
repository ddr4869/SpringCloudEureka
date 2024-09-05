package com.delivery.order_service.service;

import com.delivery.order_service.dto.FindUserFeignResponse;
import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.feign.UserServiceFeignClient;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    //private final OrdersRepository orderRepository;
    private final UserServiceFeignClient userServiceFeignClient;

    public CreateOrderResponse createOrder() {
        CommonResponse<FindUserFeignResponse> feignResponse = userServiceFeignClient.getUserByUsername("tom");
        System.out.println("user -> "+ feignResponse.data());
        System.out.println("user.toString -> "+ feignResponse.data().toString());
        System.out.println("feign user success -> " + feignResponse.data().getName());
        return CreateOrderResponse.builder()
                .orderId(1L)
                .build();
    }
}
