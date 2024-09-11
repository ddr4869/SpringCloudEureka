package com.delivery.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceOrderRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long storeId;

    @NotNull
    private Long menuId;

    @NotNull
    private BigDecimal totalPrice;

    @NotBlank
    private String deliveryAddress;
}