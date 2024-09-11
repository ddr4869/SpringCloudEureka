package com.delivery.store_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String name;

//    @Column(nullable = false)
//    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private String address;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Store(String name, String address, String phoneNumber, StoreCategory category) {
        this.name = name;
        //this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = StoreStatus.OPEN;
        this.category = category;
    }

    public void updateInfo(String name, String address, String phoneNumber) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void closeStore() {
        this.status = StoreStatus.CLOSED;
    }

    public void openStore() {
        this.status = StoreStatus.OPEN;
    }
}

