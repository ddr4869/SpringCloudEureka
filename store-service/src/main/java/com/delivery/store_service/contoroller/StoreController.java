package com.delivery.store_service.contoroller;

import com.delivery.store_service.dto.request.RegisterStoreRequest;
import com.delivery.store_service.dto.response.StoreResponse;
import com.delivery.store_service.global.success.CommonResponse;
import com.delivery.store_service.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store-service")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<StoreResponse>> createStore(@RequestBody @Valid RegisterStoreRequest registerStoreRequest) {
        StoreResponse response = storeService.registerStore(registerStoreRequest);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @GetMapping("/info")
    public ResponseEntity<CommonResponse<StoreResponse>> getStoreInfo(@RequestParam("name") String storeName) {
        StoreResponse response = storeService.findStoreByName(storeName);
        return CommonResponse.ResponseEntitySuccess(response);
    }
}
