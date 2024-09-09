package com.delivery.order_service.dto;

import com.delivery.order_service.entity.Orders;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long storeId;
    private Long menuId;
    private Orders.OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private String deliveryAddress;

    @Builder
    public OrderResponse(Long orderId, Long userId, Long storeId, Long menuId, Orders.OrderStatus orderStatus, BigDecimal totalPrice, String deliveryAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.menuId = menuId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }
}
