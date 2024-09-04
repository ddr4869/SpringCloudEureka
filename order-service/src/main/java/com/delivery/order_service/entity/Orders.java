package com.delivery.order_service.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;

    private Long storeId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;
    private String deliveryAddress;

    @CreationTimestamp
    private LocalDateTime placedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters and Setters
}
