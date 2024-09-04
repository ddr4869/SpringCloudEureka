package com.delivery.store_service.contoroller;

import com.delivery.store_service.service.StoreService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
