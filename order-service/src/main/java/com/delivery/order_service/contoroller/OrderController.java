package com.delivery.order_service.contoroller;

import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.dto.OrderResponse;
import com.delivery.order_service.dto.PlaceOrderRequest;
import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @PostMapping("/place-order")
    public ResponseEntity<CommonResponse<OrderResponse>> placeOrder(
            @RequestBody @Valid PlaceOrderRequest placeOrderRequest
    ) {
        OrderResponse response = orderService.placeOrder(placeOrderRequest);
        return CommonResponse.ResponseEntitySuccess(response);
    }

//    @PostMapping("/webclient/create")
//    public ResponseEntity<CommonResponse<Mono<OrderResponse>>>  createOrderWebClient(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//        ResponseEntity<CommonResponse<Mono<OrderResponse>>> resp = CommonResponse.ResponseEntitySuccess(orderService.createOrderWebClient());
//        log.info("Mono TEST 2  ");
//        return resp;
//    }
}
