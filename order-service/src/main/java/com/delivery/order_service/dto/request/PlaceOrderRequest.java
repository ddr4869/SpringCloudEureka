package com.delivery.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long storeId;

    @NotNull
    private List<MenuOrderRequest> menuOrders;  // Multiple menus

    @NotBlank
    private String deliveryAddress;
}
