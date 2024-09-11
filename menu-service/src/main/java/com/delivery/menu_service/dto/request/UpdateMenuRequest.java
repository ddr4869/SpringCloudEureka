package com.delivery.menu_service.dto.request;

import com.delivery.menu_service.entity.MenuItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateMenuRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private MenuItem.MenuCategory category;
}