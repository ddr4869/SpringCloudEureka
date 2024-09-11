package com.delivery.store_service.service;

import com.delivery.store_service.dto.request.RegisterStoreRequest;
import com.delivery.store_service.dto.response.StoreResponse;
import com.delivery.store_service.entity.Store;
import com.delivery.store_service.entity.StoreStatus;
import com.delivery.store_service.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreResponse registerStore(RegisterStoreRequest registerStoreRequest) {
        // check if store name is already taken
        if (storeRepository.findByName(registerStoreRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store name '" + registerStoreRequest.getName() + "' is already taken.");
        }

        Store store = Store.builder()
                .name(registerStoreRequest.getName())
                .ownerId(registerStoreRequest.getOwnerId())
                .status(StoreStatus.OPEN)
                .address(registerStoreRequest.getAddress())
                .phoneNumber(registerStoreRequest.getPhoneNumber())
                .build();

        storeRepository.save(store);
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .ownerId(store.getOwnerId())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .build();
    }

    public StoreResponse findStoreByName(String name) {
        Store store = storeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store name '" + name + "' not found."));
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .ownerId(store.getOwnerId())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .build();
    }

    public StoreResponse findStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store id '" + id + "' not found."));
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .ownerId(store.getOwnerId())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .build();
    }
}
