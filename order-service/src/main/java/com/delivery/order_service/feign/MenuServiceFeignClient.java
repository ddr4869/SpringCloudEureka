package com.delivery.order_service.feign;

import com.delivery.order_service.dto.FindMenuByNameResponse;
import com.delivery.order_service.dto.FindStoreByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "menu-service", url = "http://localhost:8080/menu-service")
public interface MenuServiceFeignClient {
    @GetMapping("/info")
    CommonResponse<FindMenuByNameResponse> getMenuById(@RequestParam("id") Long id);
}