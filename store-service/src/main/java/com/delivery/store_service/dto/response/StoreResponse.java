package com.delivery.store_service.dto.response;

import com.delivery.store_service.entity.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class StoreResponse {
    private String name;
    private Long ownerId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;

    @Builder
    public StoreResponse(String name, Long ownerId, String address, String phoneNumber, StoreStatus status) {
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}
