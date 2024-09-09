package com.delivery.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindMenuByNameResponse {
    private Long itemId;
    private MenuCategory category;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal additionalPrice;

    public enum MenuCategory {
        // 한식, 일식, 중식, 양식, 치킨, 피자, 그 외
        KOREAN, JAPANESE, CHINESE, WESTERN, CHICKEN, PIZZA, OTHER;
    }
}
