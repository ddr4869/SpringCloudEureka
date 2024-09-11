package com.delivery.order_service.dto.response;

import com.delivery.order_service.dto.request.MenuOrderRequest;
import com.delivery.order_service.entity.Orders;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long storeId;
    private Orders.OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private List<MenuOrderRequest> menuOrders;

    @Builder
    public OrderResponse(Long orderId, Long userId, Long storeId, Orders.OrderStatus orderStatus, BigDecimal totalPrice, String deliveryAddress, List<MenuOrderRequest> menuOrders) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.menuOrders = menuOrders;
    }
}
