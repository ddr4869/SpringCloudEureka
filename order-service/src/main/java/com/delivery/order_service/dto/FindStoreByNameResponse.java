package com.delivery.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindStoreByNameResponse {
    private Long storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private BigDecimal minimumOrderPrice;
    private BigDecimal deliveryFee;

    public enum StoreStatus {
        OPEN, CLOSED
    }
}

