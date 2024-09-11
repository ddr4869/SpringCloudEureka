package com.delivery.store_service.dto.request;

import com.delivery.store_service.entity.Store;
import com.delivery.store_service.entity.StoreCategory;
import com.delivery.store_service.entity.StoreStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class RegisterStoreRequest {
    @NotBlank(message = "빈칸일 수 없습니다.")
    private String name;

    @NotNull(message = "빈칸일 수 없습니다.")
    private StoreCategory category;

    @NotBlank(message = "빈칸일 수 없습니다.")
    private String address;

    @NotBlank(message = "빈칸일 수 없습니다.")
    private String phoneNumber;
}
