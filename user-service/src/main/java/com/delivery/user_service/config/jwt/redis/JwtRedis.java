package com.delivery.user_service.config.jwt.redis;

import com.delivery.user_service.config.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class JwtRedis {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtUtil jwtUtil;

    public boolean isRedisConnected() {
        try {
            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            return "PONG".equals(pong);
        } catch (Exception e) {
            return false;
        }
    }


    public void setValues(String key, String data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key,data);
    }

    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return (String) values.get(key);
    }

    public void setJwtSet(String key, List<Object> datas) {
        SetOperations<String, Object> sets = redisTemplate.opsForSet();
        for (Object data: datas) {
            sets.add(key, data);
        }
    }
    public Set<Object> getJwtSet(String key) {
        SetOperations<String, Object> sets = redisTemplate.opsForSet();
        return sets.members(key);
    }

    public void setJwtHash(String key, HashMap<String, Object> hashData) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, hashData);
    }
    public Map<String, Object> getJwtHash(String key) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    public void insertRefreshToken(String token) {
        HashMap<String, Object> payloads = new HashMap<>();
        Claims claims = jwtUtil.getClaims(token);
        payloads.put("userName", String.valueOf(claims.get("userName")));
        payloads.put("issuedAt",claims.getIssuedAt());
        payloads.put("expired", claims.getExpiration());
        setJwtHash(token, payloads);
    }

    public void delKey(String key) {
        redisTemplate.delete(key);
    }
}
