package com.delivery.user_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String address;
    private boolean isDefault;

    @Builder
    public UserAddress(Long addressId, User user, String address, boolean isDefault) {
        this.addressId = addressId;
        this.user = user;
        this.address = address;
        this.isDefault = isDefault;
    }
}