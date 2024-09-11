package com.delivery.order_service.contoroller;

import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.dto.OrderResponse;
import com.delivery.order_service.dto.PlaceOrderRequest;
import com.delivery.order_service.dto.response.CreateOrderResponse;
import com.delivery.order_service.entity.Orders;
import com.delivery.order_service.global.success.CommonResponse;
import com.delivery.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUser(userId);
        return CommonResponse.ResponseEntitySuccess(orders);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getOrdersByStore(@PathVariable Long storeId) {
        List<OrderResponse> orders = orderService.getOrdersByStore(storeId);
        return CommonResponse.ResponseEntitySuccess(orders);
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<CommonResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Orders.OrderStatus status
    ) {
        OrderResponse order = orderService.updateOrderStatus(orderId, status);
        return CommonResponse.ResponseEntitySuccess(order);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<CommonResponse<OrderResponse>> cancelOrder(@PathVariable Long orderId) {
        OrderResponse order = orderService.cancelOrder(orderId);
        return CommonResponse.ResponseEntitySuccess(order);
    }

//    @PostMapping("/webclient/create")
//    public ResponseEntity<CommonResponse<Mono<OrderResponse>>>  createOrderWebClient(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//        ResponseEntity<CommonResponse<Mono<OrderResponse>>> resp = CommonResponse.ResponseEntitySuccess(orderService.createOrderWebClient());
//        log.info("Mono TEST 2  ");
//        return resp;
//    }
}
