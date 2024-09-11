package com.delivery.store_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Setter
    private String name;

//    @Column(nullable = false)
//    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Setter
    private String address;
    @Setter
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Setter
    private BigDecimal minimumOrderPrice;
    @Setter
    private BigDecimal deliveryFee;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Store(String name, String address, String phoneNumber, StoreCategory category, BigDecimal minimumOrderPrice, BigDecimal deliveryFee) {
        this.name = name;
        //this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = StoreStatus.OPEN;
        this.category = category;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryFee = deliveryFee;
    }

    public void updateInfo(String name, String address, String phoneNumber, BigDecimal minimumOrderPrice, BigDecimal deliveryFee) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
        if (minimumOrderPrice != null) {
            this.minimumOrderPrice = minimumOrderPrice;
        }
        if (deliveryFee != null) {
            this.deliveryFee = deliveryFee;
        }
    }

    public void closeStore() {
        this.status = StoreStatus.CLOSED;
    }

    public void openStore() {
        this.status = StoreStatus.OPEN;
    }
}

