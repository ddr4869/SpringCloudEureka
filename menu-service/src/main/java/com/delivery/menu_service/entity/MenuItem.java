package com.delivery.menu_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_item")
@Getter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    private String name;
    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private MenuItemStatus status;

    @OneToOne
    @JoinColumn(name = "menuOptionId")
    private MenuItemOption menuItemOption;

    public enum MenuItemStatus {
        AVAILABLE, UNAVAILABLE
    }

    @Builder
    public MenuItem(MenuCategory category, String name, String description, BigDecimal price, MenuItemStatus status) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public enum MenuCategory {
        // 한식, 일식, 중식, 양식, 치킨, 피자, 그 외
        KOREAN, JAPANESE, CHINESE, WESTERN, CHICKEN, PIZZA, OTHER;
    }
}