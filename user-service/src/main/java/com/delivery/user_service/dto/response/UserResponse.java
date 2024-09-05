package com.delivery.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String email;
    private String name;
    private Long userId;

    @Builder
    public UserResponse(String email, String name, Long userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
