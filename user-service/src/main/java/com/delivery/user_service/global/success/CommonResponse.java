package com.delivery.user_service.global.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @param errors Errors가 없다면 응답이 내려가지 않게 처리
 */
@Builder
@Schema(description = "공통 응답")
public record CommonResponse<T>(
        @Schema(description = "status code")  int status,
        @Schema(description = "응답 코드")  String code,
        @Schema(description = "응답 메시지")  String message,
        @Schema(description = "응답 데이터")  T data,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<ValidationError> errors) {

    public static <T> CommonResponse<T> CommonResponseSuccess(T data) {
        return CommonResponse.<T>builder()
                .status(200)
                .code("SUCCESS")
                .message("OK")
                .data(data)
                .build();
    }

    public static CommonResponse<String> CommonResponseSuccessMessage() {
        return CommonResponse.<String>builder()
                .status(200)
                .code("SUCCESS")
                .message("OK")
                .data("SUCCESS")
                .build();
    }

    public static <T> CommonResponse<T> CommonResponseUnauthorized(String message) {
        return CommonResponse.<T>builder()
                .status(401)
                .code("Unauthorized")
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> CommonResponseBadRequest(String message) {
        return CommonResponse.<T>builder()
                .status(400)
                .code("Bad request")
                .message(message)
                .build();
    }

    public static <T> ResponseEntity<CommonResponse<T>> ResponseEntitySuccess(T data) {
        return ResponseEntity.status(200).body(CommonResponseSuccess(data));
    }

    public static ResponseEntity<CommonResponse<String>> ResponseEntitySuccessMessage() {
        return ResponseEntity.status(200).body(CommonResponseSuccess("SUCCESS"));
    }

    public static <T> ResponseEntity<CommonResponse<T>> ResponseEntityBadRequest(String message) {
        return ResponseEntity.status(400).body(CommonResponseBadRequest(message));
    }

    public static <T> ResponseEntity<CommonResponse<T>> ResponseEntityUnauthorized(String message) {
        return ResponseEntity.status(401).body(CommonResponseUnauthorized(message));
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
