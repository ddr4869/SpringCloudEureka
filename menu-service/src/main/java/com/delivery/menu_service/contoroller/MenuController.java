package com.delivery.menu_service.contoroller;

import com.delivery.menu_service.dto.request.RegisterMenuRequest;
import com.delivery.menu_service.dto.response.MenuResponse;
import com.delivery.menu_service.global.success.CommonResponse;
import com.delivery.menu_service.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<MenuResponse>> registerMenu(@RequestBody @Valid RegisterMenuRequest request) {
        log.info("start");
        MenuResponse response = menuService.RegisterMenu(request);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @GetMapping("/info")
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuInfo(@RequestParam("name") String menuName) {
        MenuResponse response = menuService.findMenuByName(menuName);
        return CommonResponse.ResponseEntitySuccess(response);
    }

}
