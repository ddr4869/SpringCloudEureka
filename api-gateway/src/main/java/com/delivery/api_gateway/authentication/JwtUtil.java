package com.delivery.api_gateway.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.crypto.SecretKey;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
public class JwtUtil {
    @Value("${jwt.secret}")
    public String secret;
    @Value("${jwt.expired.access}")
    public Long accessExpired;
    @Value("${jwt.expired.refresh}")
    public Long refreshExpired;
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        secret=Encoders.BASE64.encode(secret.getBytes());
        this.secretKey=Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Request Header 에서 토큰 정보 추출
    public String resolveToken(String bearerToken) {
        log.info("bearerToken : {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            return false;
        }
    }
    public Claims getClaims(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return claims.getPayload();
        } catch (RuntimeException e) {
            log.info("Jwt claims something wrong : {}", token);
            throw e;
        }
    }

    public String getClaimsUsername(String token) {
        return getClaims(token).get("userName").toString();
    }
}
