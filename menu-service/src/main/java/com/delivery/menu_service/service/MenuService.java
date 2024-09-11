package com.delivery.menu_service.service;

import com.delivery.menu_service.dto.request.RegisterMenuRequest;
import com.delivery.menu_service.dto.response.MenuResponse;
import com.delivery.menu_service.entity.MenuItem;
import com.delivery.menu_service.entity.MenuItemOption;
import com.delivery.menu_service.repository.MenuItemOptionRepository;
import com.delivery.menu_service.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Transactional()
public class MenuService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemOptionRepository menuItemOptionRepository;
    public MenuResponse RegisterMenu(RegisterMenuRequest registerMenuRequest) {
        if (menuItemRepository.findByName(registerMenuRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu item with name '" + registerMenuRequest.getName() + "' already exists.");
        }

        MenuItemOption menuItemOption = MenuItemOption.builder()
                .name(registerMenuRequest.getName())
                .additionalPrice(registerMenuRequest.getAdditionalPrice())
                .build();

        menuItemOptionRepository.save(menuItemOption);

        MenuItem menuItem = MenuItem.builder()
                .category(registerMenuRequest.getCategory())
                .name(registerMenuRequest.getName())
                .description(registerMenuRequest.getDescription())
                .status(MenuItem.MenuItemStatus.AVAILABLE)
                .price(registerMenuRequest.getPrice())
                .menuItemOption(menuItemOption)
                .description(registerMenuRequest.getDescription())
                .build();
        menuItemRepository.save(menuItem);

        return MenuResponse.builder()
                .name(registerMenuRequest.getName())
                .itemId(menuItem.getItemId())
                .category(registerMenuRequest.getCategory())
                .price(registerMenuRequest.getPrice())
                .additionalPrice(registerMenuRequest.getAdditionalPrice())
                .description(registerMenuRequest.getDescription())
                .build();
    }

    public MenuResponse findMenuByName(String name) {
        MenuItem menuItem = menuItemRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item with name '" + name + "' not found."));
        return MenuResponse.builder()
                .name(menuItem.getName())
                .itemId(menuItem.getItemId())
                .category(menuItem.getCategory())
                .price(menuItem.getPrice())
                .additionalPrice(menuItem.getMenuItemOption().getAdditionalPrice())
                .description(menuItem.getDescription())
                .build();
    }

    public MenuResponse findMenuById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item with id '" + id + "' not found."));
        return MenuResponse.builder()
                .name(menuItem.getName())
                .itemId(menuItem.getItemId())
                .category(menuItem.getCategory())
                .price(menuItem.getPrice())
                .additionalPrice(menuItem.getMenuItemOption().getAdditionalPrice())
                .description(menuItem.getDescription())
                .build();
    }
}
