package com.delivery.menu_service.contoroller;

import com.delivery.menu_service.dto.request.RegisterMenuRequest;
import com.delivery.menu_service.dto.request.UpdateMenuRequest;
import com.delivery.menu_service.dto.response.MenuResponse;
import com.delivery.menu_service.entity.MenuItem;
import com.delivery.menu_service.global.success.CommonResponse;
import com.delivery.menu_service.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{itemId}")
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenuItem(@PathVariable Long itemId, @RequestBody @Valid UpdateMenuRequest request) {
        MenuResponse response = menuService.updateMenuItem(itemId, request);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<CommonResponse<String>> deleteMenuItem(@PathVariable Long itemId) {
        menuService.deleteMenuItem(itemId);
        return CommonResponse.ResponseEntitySuccessMessage();
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getMenuItemsByStore(@PathVariable Long storeId) {
        List<MenuResponse> response = menuService.getMenuItemsByStore(storeId);
        return CommonResponse.ResponseEntitySuccess(response);
    }

    @PutMapping("/status/{itemId}")
    public ResponseEntity<CommonResponse<MenuResponse>> changeMenuItemStatus(@PathVariable Long itemId) {
        MenuResponse response = menuService.changeMenuItemStatus(itemId);
        return CommonResponse.ResponseEntitySuccess(response);
    }

}
