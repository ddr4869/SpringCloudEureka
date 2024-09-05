package com.delivery.user_service.config.security;

import com.delivery.user_service.config.filter.JwtAuthenticationFilter;
import com.delivery.user_service.config.filter.RequestValidationFilter;
import com.delivery.user_service.config.filter.entrypoint.CustomAuthenticationEntryPoint;
import com.delivery.user_service.config.filter.entrypoint.CustomJwtEntryPoint;
import com.delivery.user_service.config.jwt.JwtUtil;
import com.delivery.user_service.config.login.LoginFailureHandler;
import com.delivery.user_service.config.login.LoginSuccessHandler;
import com.delivery.user_service.global.error.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.PrintWriter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final RequestValidationFilter requestValidationFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomJwtEntryPoint customJwtEntryPoint;
    private final JwtUtil jwtUtil;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                // 1,2. 로그인 페이지 설정 -> 사용자가 로그인 페이지에 접근, 로그인 정보를 서버에 제출
                .formLogin(form -> form
                        // .loginPage("/login")
                        // 8 로그인 성공 - AuthenticationSuccessHandler 호출
                        .successHandler(loginSuccessHandler)
                        // 9 로그인 실패 - AuthenticationFailureHandler 호출
                        .failureHandler(loginFailureHandler)
                        .permitAll())
                //.formLogin(withDefaults())
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.addAllowedOriginPattern("*"); // 모든 ip에 응답을 허용합니다.
                            corsConfiguration.addAllowedMethod("*");
                            corsConfiguration.addAllowedHeader("*");
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setMaxAge(3600L);
                            return corsConfiguration;
                        }))
                // 3. UsernamePasswordAuthenticationFilter 전 jwt 인증 filter(JwtAuthenticationFilter)
                // 4. UsernamePasswordAuthenticationFilter는 로그인 정보를 가로채 AuthenticationManager에게 인증을 요청한다.
                // api-gateway에서 필터링
//                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
//                    .exceptionHandling(exceptionConfig -> exceptionConfig.accessDeniedHandler(customJwtEntryPoint))
                // set security filter if bearer token is valid, anyRequest is authenticated, else permitAll

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/hello").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/user/refresh, /api/user/favorites/**").authenticated()
                        .anyRequest().permitAll() )  //authenticated()
                        // .requestMatchers("/login**", "/api/user/signup", "/api/user/refresh", "/swagger-ui/**", "/api-docs/**").permitAll()
                .exceptionHandling( exceptionConfig -> exceptionConfig.accessDeniedHandler(customAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // 1
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                log.info("AccessDeniedHandler");
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {
        private final HttpStatus status;
        private final String message;
    }
}
