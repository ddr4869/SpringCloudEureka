package com.delivery.store_service.dto.response;

import com.delivery.store_service.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class StoreResponse {
    private Long storeId;
    private String name;
    private Long ownerId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;

    @Builder
    public StoreResponse(Long storeId, String name, Long ownerId, String address, String phoneNumber, StoreStatus status) {
        this.storeId = storeId;
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}
