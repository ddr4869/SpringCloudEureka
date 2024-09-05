package com.delivery.api_gateway;

import com.delivery.api_gateway.authentication.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@ComponentScan
public class GatewayTest {

//    @Autowired
//    private Environment env;  // 환경 변수 주입
    @Value("${jwt.secret}")
    public String secret;
    @Test
    public void test() {
        //System.out.println(env.getProperty("jwt.secret"));
        System.out.println(secret);
    }
}
