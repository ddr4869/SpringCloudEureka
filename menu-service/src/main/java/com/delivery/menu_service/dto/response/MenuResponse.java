package com.delivery.menu_service.dto.response;

import com.delivery.menu_service.entity.MenuItem;
import com.delivery.menu_service.entity.MenuItemOption;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuResponse {
    private Long itemId;
    private MenuItem.MenuCategory category;
    private String name;
    private String description;
    private Long storeId;
    private MenuItem.MenuItemStatus status;
    private BigDecimal price;
    private BigDecimal additionalPrice;

    @Builder
    public MenuResponse(Long itemId, MenuItem.MenuCategory category, String name, String description, BigDecimal price, BigDecimal additionalPrice, MenuItem.MenuItemStatus status, Long storeId) {
        this.itemId = itemId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.price = price;
        this.additionalPrice = additionalPrice;
        this.status = status;
    }
}
