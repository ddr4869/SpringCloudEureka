package com.delivery.menu_service.global.error.handler;

import com.delivery.menu_service.global.error.errorCode.CommonErrorCode;
import com.delivery.menu_service.global.error.errorCode.ErrorCode;
import com.delivery.menu_service.global.error.exception.RestApiException;
import com.delivery.menu_service.global.success.CommonResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleCustomException(BadRequestException e) {
        log.info("handleBadRequest", e);
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleCustomException(FeignException e) {
        log.info("FeignException", e);
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        return handleExceptionInternal(errorCode, "Feign response time out");
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("MethodArgumentTypeMismatchException", e);
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        for (String str : errors) {
            log.info("{}",str);
        }
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleCustomException(MethodArgumentTypeMismatchException e) {
        log.info("MethodArgumentTypeMismatchException", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleIllegalArgument(DuplicateKeyException e) {
        log.info("DuplicateKeyException", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.info("handleIllegalArgument", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    // 나머지 Exception 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.info("handleAllException", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    public static ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    public ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    public ResponseEntity<Object> handleExceptionInternal(MethodArgumentNotValidException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    public static CommonResponse<Object> makeErrorResponse(ErrorCode errorCode) {
        return CommonResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    public CommonResponse<Object> makeErrorResponse(MethodArgumentNotValidException e, ErrorCode errorCode) {
        List<CommonResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(CommonResponse.ValidationError::of)
                .collect(Collectors.toList());

        return CommonResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }

    public CommonResponse<Object> makeErrorResponse(ErrorCode errorCode, String message) {
        return CommonResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
