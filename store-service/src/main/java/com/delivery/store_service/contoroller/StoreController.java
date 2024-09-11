package com.delivery.store_service.contoroller;

import com.delivery.store_service.dto.request.RegisterStoreRequest;
import com.delivery.store_service.dto.request.UpdateStoreRequest;
import com.delivery.store_service.dto.response.StoreResponse;
import com.delivery.store_service.entity.StoreCategory;
import com.delivery.store_service.entity.StoreStatus;
import com.delivery.store_service.global.success.CommonResponse;
import com.delivery.store_service.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommonResponse<StoreResponse>> getStoreInfo(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ) {
        if (id != null) {
            StoreResponse response = storeService.findStoreById(id);
            return CommonResponse.ResponseEntitySuccess(response);
        } else if (name != null) {
            StoreResponse response = storeService.findStoreByName(name);
            return CommonResponse.ResponseEntitySuccess(response);
        } else {
            return CommonResponse.ResponseEntityBadRequest("id 또는 name 중 하나는 필수입니다.");
        }
    }

    @PutMapping("/update/{storeId}")
    public ResponseEntity<CommonResponse<StoreResponse>> updateStore(
            @PathVariable Long storeId,
            @RequestBody @Valid UpdateStoreRequest updateStoreRequest
    ) {
        StoreResponse response = storeService.updateStore(storeId, updateStoreRequest);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @PutMapping("/status/update/{storeId}")
    public ResponseEntity<CommonResponse<StoreResponse>> updateStoreStatus(
            @PathVariable Long storeId
    ) {

        StoreResponse response = storeService.updateStoreStatus(storeId);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<StoreResponse>>> getAllStores() {
        List<StoreResponse> stores = storeService.getAllStores();
        return CommonResponse.ResponseEntitySuccess(stores);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<StoreResponse>>> searchStores(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) StoreCategory category
    ) {
        List<StoreResponse> stores = storeService.searchStores(name, address, category);
        return CommonResponse.ResponseEntitySuccess(stores);
    }
}
