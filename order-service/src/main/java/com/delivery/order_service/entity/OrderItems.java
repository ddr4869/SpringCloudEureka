package com.delivery.order_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
@Getter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    private Long menuItem;

    private int quantity;
    private BigDecimal price;

    @Builder
    public OrderItems(Orders order, Long menuItem, int quantity, BigDecimal price) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }
}
