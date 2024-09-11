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
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuInfo(@RequestParam(required = false) Long id,
                                                                    @RequestParam(required = false) String name) {
        if (id != null) {
            MenuResponse response = menuService.findMenuById(id);
            return CommonResponse.ResponseEntitySuccess(response);
        } else if (name != null) {
            MenuResponse response = menuService.findMenuByName(name);
            return CommonResponse.ResponseEntitySuccess(response);
        } else {
            return CommonResponse.ResponseEntityBadRequest("id 또는 username 중 하나는 필수입니다.");
        }
    }

}
