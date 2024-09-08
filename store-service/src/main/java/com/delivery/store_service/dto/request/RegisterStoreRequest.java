package com.delivery.store_service.dto.request;

import com.delivery.store_service.entity.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class RegisterStoreRequest {
    @NotBlank(message = "빈칸일 수 없습니다.")
    private String name;

    @NotNull(message = "빈칸일 수 없습니다.")
    private Long ownerId;

    @NotBlank(message = "빈칸일 수 없습니다.")
    private String address;

    @NotBlank(message = "빈칸일 수 없습니다.")
    private String phoneNumber;
}
