package com.delivery.menu_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindStoreByNameResponse {
    private Long storeId;
    private String name;
    private Long ownerId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;

    public enum StoreStatus {
        OPEN, CLOSED
    }
}

