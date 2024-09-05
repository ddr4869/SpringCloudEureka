package com.delivery.user_service.config.jwt;

import com.delivery.user_service.config.jwt.model.CustomJwtClaims;
import com.delivery.user_service.config.security.UserDetailsServiceImpl;
import com.delivery.user_service.repository.AuthorityRepository;
import com.delivery.user_service.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

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

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    protected void init() {
        secret=Encoders.BASE64.encode(secret.getBytes());
        this.secretKey=Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createAccessJwt(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createJwt(username, accessExpired);
    }
    public String createRefreshJwt(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createJwt(username, refreshExpired);
    }

    public String createJwt(String username, Long expired) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.builder()
                .claim("userName", username)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expired))
                .signWith(secretKey)
                .compact();
    }

    // Request Header 에서 토큰 정보 추출
    public String resolveToken(String bearerToken) {
        log.info("bearerToken : {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = getClaims(token);
        String username = String.valueOf(claims.get("userName"));
        if (username == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository, authorityRepository);
        UserDetails user = userDetailsService.loadUserByUsername((String) claims.get("userName"));
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
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
    public CustomJwtClaims getCustomClaims(Map<String, Object> payloads) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (payloads.isEmpty()) {
            log.info("!!! expired or invalid token !!!");
        }
        CustomJwtClaims customJwtClaims= new CustomJwtClaims();
        customJwtClaims.access_token = createAccessJwt((String) payloads.get("userName"));
        customJwtClaims.issuer = (String) payloads.get("userName");
        customJwtClaims.issuedAt = (Date) payloads.get("issuedAt");
        customJwtClaims.expired = (Date) payloads.get("expired");
        return customJwtClaims;
    }
}
