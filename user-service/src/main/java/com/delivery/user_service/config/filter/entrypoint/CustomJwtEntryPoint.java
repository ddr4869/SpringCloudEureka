package com.delivery.user_service.config.filter.entrypoint;

import com.delivery.user_service.global.error.errorCode.CommonErrorCode;
import com.delivery.user_service.global.error.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomJwtEntryPoint implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("!!! CustomJwtEntryPoint !!!");
        jwtErrResponse(response, CommonErrorCode.INVALID_TOKEN);
    }
    private void jwtErrResponse(HttpServletResponse response, CommonErrorCode code) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<Object> errorResponse = GlobalExceptionHandler.handleExceptionInternal(code);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse.getBody()));
    }
}
