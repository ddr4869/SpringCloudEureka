package com.delivery.user_service.service;

import com.delivery.user_service.dto.request.UserRequest;
import com.delivery.user_service.dto.response.UserResponse;
import com.delivery.user_service.entity.Authority;
import com.delivery.user_service.entity.User;
import com.delivery.user_service.entity.UserAddress;
import com.delivery.user_service.repository.AuthorityRepository;
import com.delivery.user_service.repository.UserAddressRepository;
import com.delivery.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    // sign up user
    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getName()).isPresent()) {
            throw new DuplicateKeyException("Username '" + userRequest.getName() + "' is already taken.");
        }
        User user = User.builder()
                .username(userRequest.getName())
                .email(userRequest.getEmail())
                .passwordHash(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .build();

        userRepository.save(user);

        Authority authority = new Authority(Authority.Authority_Type.ROLE_USER, user);
        authorityRepository.save(authority);

        UserAddress userAddress = UserAddress.builder()
                .user(user)
                .address(userRequest.getAddress())
                .isDefault(true)
                .build();
        userAddressRepository.save(userAddress);

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .userId(user.getId())
                .build();
    }

    // find user by username
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .userId(user.getId())
                .build();
    }
}
