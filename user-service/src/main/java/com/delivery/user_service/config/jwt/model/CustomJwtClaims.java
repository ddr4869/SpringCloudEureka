package com.delivery.user_service.config.jwt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Schema(description = "JWT 토큰")
public class CustomJwtClaims {
    @NotNull
    @Schema(description = "access token")
    public String access_token;
    @NotNull
    @Schema(description = "발행자")
    public String issuer;
    @NotNull
    @Schema(description = "토큰 발급 시간")
    public Date issuedAt;
    @NotNull
    @Schema(description = "토큰 만료 시간")
    public Date expired;
}
