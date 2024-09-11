package com.delivery.order_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuOrderRequest {
    @NotNull
    private Long menuId;

    @NotNull
    private int quantity;  // Quantity of the menu item
}