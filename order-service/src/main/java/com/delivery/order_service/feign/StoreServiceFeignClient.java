package com.delivery.order_service.feign;

import com.delivery.order_service.dto.FindStoreByNameResponse;
import com.delivery.order_service.dto.FindUserByNameResponse;
import com.delivery.order_service.global.success.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store-service", url = "http://localhost:8080/store-service")
public interface StoreServiceFeignClient {
    @GetMapping("/info")
    CommonResponse<FindStoreByNameResponse> getStoreById(@RequestParam("id") Long id);
}