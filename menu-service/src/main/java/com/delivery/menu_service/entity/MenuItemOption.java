package com.delivery.menu_service.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_item_option")
@Getter
public class MenuItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuOptionId;

    @OneToOne(mappedBy = "menuItemOption")
    private MenuItem menuItem;

    private String name;
    private BigDecimal additionalPrice;

    @Builder
    public MenuItemOption(String name, BigDecimal additionalPrice) {
        this.name = name;
        this.additionalPrice = additionalPrice;
    }
}
