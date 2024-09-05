package com.delivery.order_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;

    private Long storeId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;
    private String deliveryAddress;

    @CreationTimestamp
    private LocalDateTime placedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Orders(Long userId, Long storeId, OrderStatus orderStatus, BigDecimal totalPrice, String deliveryAddress) {
        this.userId = userId;
        this.storeId = storeId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
    }

    public enum OrderStatus {
        PLACED, PREPARING, DELIVERING, COMPLETED, CANCELLED
    }

}
