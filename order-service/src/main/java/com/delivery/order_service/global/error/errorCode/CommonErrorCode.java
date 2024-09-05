package com.delivery.order_service.global.error.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Request something wrong"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Token expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token");
    ;

    private final HttpStatus httpStatus;
    private final String message;
}