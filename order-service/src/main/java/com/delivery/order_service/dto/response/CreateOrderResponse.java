package com.delivery.order_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateOrderResponse {
    private Long orderId;

    @Builder
    public CreateOrderResponse(Long orderId) {
        this.orderId = orderId;
    }
}
