package com.delivery.user_service.contoroller;

import com.delivery.user_service.dto.request.UserRequest;
import com.delivery.user_service.dto.response.UserResponse;
import com.delivery.user_service.entity.User;
import com.delivery.user_service.global.success.CommonResponse;
import com.delivery.user_service.service.UserService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {
    private final UserService userService;

    // ping
    @GetMapping("/ping")
    @Timed(value = "users.status", longTask = true)
    public String ping() {
        return "pong";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<UserResponse>> signUp(@RequestBody @Valid UserRequest userRequest) {
        UserResponse response = userService.signUp(userRequest);
        //return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @GetMapping("/info")
    public ResponseEntity<CommonResponse<UserResponse>> findUserInfo(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            UserResponse response = userService.findById(id);
            return CommonResponse.ResponseEntitySuccess(response);
        } else if (name != null) {
            UserResponse response = userService.findByUsername(name);
            return CommonResponse.ResponseEntitySuccess(response);
        } else {
            return CommonResponse.ResponseEntityBadRequest("id 또는 name 중 하나는 필수입니다.");
        }
    }
}
