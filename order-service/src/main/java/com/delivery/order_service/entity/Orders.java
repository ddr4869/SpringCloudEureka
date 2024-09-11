package com.delivery.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private Long menuId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;
    private String deliveryAddress;

    @CreationTimestamp
    private LocalDateTime placedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Orders(Long userId, Long storeId, Long menuId, OrderStatus orderStatus, BigDecimal totalPrice, String deliveryAddress, List<OrderItems> orderItems) {
        this.userId = userId;
        this.storeId = storeId;
        this.menuId = menuId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
    }

    public enum OrderStatus {
        PLACED, PREPARING, DELIVERING, COMPLETED, CANCELLED
    }

}
