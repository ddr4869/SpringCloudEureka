package com.delivery.menu_service.dto.request;

import com.delivery.menu_service.entity.MenuItem;
import com.delivery.menu_service.entity.MenuItemOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.math.BigDecimal;

@Data
public class RegisterMenuRequest {
    @NotNull(message = "메뉴 카테고리를 입력해주세요.")
    private MenuItem.MenuCategory category;
    @NotNull(message = "메뉴 이름을 입력해주세요.")
    private String name;
    private String description;
    @NotNull(message = "메뉴 가격을 입력해주세요.")
    private BigDecimal price;
    @NotNull(message = "추가 가격을 입력해주세요.")
    private BigDecimal additionalPrice;
}
