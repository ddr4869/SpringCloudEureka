package com.delivery.order_service.contoroller;

import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<CreateOrderResponse>> createOrder() {
        return CommonResponse.ResponseEntitySuccess(orderService.createOrder());
    }

}
