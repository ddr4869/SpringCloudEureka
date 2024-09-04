package com.delivery.order_service.entity;


import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED, PREPARING, DELIVERING, COMPLETED, CANCELLED
}
