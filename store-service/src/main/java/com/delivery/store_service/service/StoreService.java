package com.delivery.store_service.service;

import com.delivery.store_service.dto.request.RegisterStoreRequest;
import com.delivery.store_service.dto.request.UpdateStoreRequest;
import com.delivery.store_service.dto.response.StoreResponse;
import com.delivery.store_service.entity.Store;
import com.delivery.store_service.entity.StoreCategory;
import com.delivery.store_service.entity.StoreStatus;
import com.delivery.store_service.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreResponse registerStore(RegisterStoreRequest registerStoreRequest) {
        if (storeRepository.findByName(registerStoreRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store name '" + registerStoreRequest.getName() + "' is already taken.");
        }

        Store store = Store.builder()
                .name(registerStoreRequest.getName())
                .category(registerStoreRequest.getCategory())
                .address(registerStoreRequest.getAddress())
                .phoneNumber(registerStoreRequest.getPhoneNumber())
                .minimumOrderPrice(registerStoreRequest.getMinimumOrderPrice())
                .deliveryFee(registerStoreRequest.getDeliveryFee())
                .build();

        storeRepository.save(store);
        return ConvertStoreToStoreResponse(store);
    }

    public StoreResponse findStoreByName(String name) {
        Store store = storeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store name '" + name + "' not found."));
        return ConvertStoreToStoreResponse(store);
    }

    public StoreResponse findStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store id '" + id + "' not found."));
        return ConvertStoreToStoreResponse(store);
    }

    public StoreResponse updateStore(Long storeId, UpdateStoreRequest updateStoreRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found."));

        if (updateStoreRequest.getName() != null && !updateStoreRequest.getName().isEmpty()) {
            store.setName(updateStoreRequest.getName());
        }
        if (updateStoreRequest.getAddress() != null && !updateStoreRequest.getAddress().isEmpty()) {
            store.setAddress(updateStoreRequest.getAddress());
        }
        if (updateStoreRequest.getPhoneNumber() != null && !updateStoreRequest.getPhoneNumber().isEmpty()) {
            store.setPhoneNumber(updateStoreRequest.getPhoneNumber());
        }
        if (updateStoreRequest.getMinimumOrderPrice() != null) {
            store.setMinimumOrderPrice(updateStoreRequest.getMinimumOrderPrice());
        }
        if (updateStoreRequest.getDeliveryFee() != null) {
            store.setDeliveryFee(updateStoreRequest.getDeliveryFee());
        }

        store.updateInfo(updateStoreRequest.getName(), updateStoreRequest.getAddress(), updateStoreRequest.getPhoneNumber(), updateStoreRequest.getMinimumOrderPrice(), updateStoreRequest.getDeliveryFee());
        storeRepository.save(store);

        return ConvertStoreToStoreResponse(store);
    }

    public StoreResponse updateStoreStatus(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found."));

        if (store.getStatus() == StoreStatus.OPEN) {
            store.closeStore();
        } else {
            store.openStore();
        }
        storeRepository.save(store);
        return ConvertStoreToStoreResponse(store);
    }

    // 3. Get all stores
    public List<StoreResponse> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(this::ConvertStoreToStoreResponse)
                .collect(Collectors.toList());
    }

    // 4. Search stores by name, category, or location
    public List<StoreResponse> searchStores(String name, String address, StoreCategory category) {
        List<Store> stores = storeRepository.searchStores(name, address, category);
        return stores.stream().map(this::ConvertStoreToStoreResponse)
                .collect(Collectors.toList());
    }

    public StoreResponse ConvertStoreToStoreResponse(Store store) {
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .category(store.getCategory())
                .minimumOrderPrice(store.getMinimumOrderPrice())
                .deliveryFee(store.getDeliveryFee())
                .build();
    }
}
