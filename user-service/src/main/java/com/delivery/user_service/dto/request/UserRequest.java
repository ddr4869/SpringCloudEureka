package com.delivery.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @Email
    @NotNull(message = "빈칸일 수 없습니다.")
    @Size(min = 2, message = "2자 이상이어야 합니다.")
    private String email;

    @NotBlank(message = "빈칸일 수 없습니다.")
    @Size(min = 8, message = "8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "빈칸일 수 없습니다.")
    @Size(min = 2, message = "2자 이상이어야 합니다.")
    private String name;

    @NotBlank(message = "빈칸일 수 없습니다.")
    @Size(min = 10, max = 11, message = "10자 이상 11자 이하이어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "빈칸일 수 없습니다.")
    @Size(min = 2, message = "2자 이상이어야 합니다.")
    private String address;
}
