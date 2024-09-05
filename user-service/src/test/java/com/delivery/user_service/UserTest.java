package com.delivery.user_service;

import com.delivery.user_service.entity.User;
import com.delivery.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ComponentScan
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        System.out.println("가");
        userRepository.findByUsername("tom").orElseThrow(() -> new DuplicateKeyException("username is already exist"));
        System.out.println("나");
    }
}
