package com.delivery.user_service.config.login;

import com.delivery.user_service.config.jwt.JwtUtil;
import com.delivery.user_service.config.login.model.LoginSuccessResponse;
import com.delivery.user_service.config.jwt.redis.JwtRedis;
import com.delivery.user_service.global.success.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final JwtRedis jwtRedis;
    @Override
    // 8 Authentication객체 성공적으로 반환 시 호출
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            log.info("Authentication name : {}",authentication.getName());

            String accessToken = jwtUtil.createAccessJwt(authentication.getName());
            String refreshToken = jwtUtil.createRefreshJwt(authentication.getName());

            jwtRedis.insertRefreshToken(refreshToken);

            LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
            loginSuccessResponse.access_token = accessToken;
            loginSuccessResponse.refresh_token = refreshToken;
            loginSuccessResponse.access_token_expired = new Date(System.currentTimeMillis()+jwtUtil.accessExpired);
            loginSuccessResponse.refresh_token_expired = new Date(System.currentTimeMillis()+jwtUtil.refreshExpired);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(
                    CommonResponse.
                            builder().
                            status(200).
                            code("SUCCESS").
                            message("OK").
                            data(loginSuccessResponse).
                            build()
            );
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(String.valueOf(jsonResponse));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
