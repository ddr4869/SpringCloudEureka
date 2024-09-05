package com.delivery.menu_service.contoroller;

import com.delivery.menu_service.service.MenuService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu-service")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
