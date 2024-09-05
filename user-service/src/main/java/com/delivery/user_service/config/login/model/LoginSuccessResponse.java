package com.delivery.user_service.config.login.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class LoginSuccessResponse {
    @NotNull
    public String access_token;

    @NotNull
    public String refresh_token;

    @NotNull
    public Date refresh_token_expired;

    @NotNull
    public Date access_token_expired;
}
