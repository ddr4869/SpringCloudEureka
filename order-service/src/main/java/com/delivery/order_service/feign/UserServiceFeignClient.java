package com.delivery.order_service.feign;

import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8080/user-service")
public interface UserServiceFeignClient {
    @GetMapping("/info")
    CommonResponse<FindUserByNameResponse> getUserByUsername(@RequestParam("username") String username);
}