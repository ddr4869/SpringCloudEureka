package com.delivery.store_service.dto.response;

import com.delivery.store_service.entity.StoreCategory;
import com.delivery.store_service.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreResponse {
    private Long storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private StoreCategory category;
    private BigDecimal minimumOrderPrice;
    private BigDecimal deliveryFee;

    @Builder
    public StoreResponse(Long storeId, String name, String address, String phoneNumber, StoreStatus status, StoreCategory category, BigDecimal minimumOrderPrice, BigDecimal deliveryFee) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.category = category;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryFee = deliveryFee;
    }
}
