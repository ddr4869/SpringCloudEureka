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
    private BigDecimal price;
    private BigDecimal additionalPrice;

    @Builder
    public MenuResponse(Long itemId, MenuItem.MenuCategory category, String name, String description, BigDecimal price, BigDecimal additionalPrice) {
        this.itemId = itemId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.additionalPrice = additionalPrice;
    }
}
