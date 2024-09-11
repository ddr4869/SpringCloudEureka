package com.delivery.store_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateStoreRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private BigDecimal minimumOrderPrice;
    private BigDecimal deliveryFee;
}