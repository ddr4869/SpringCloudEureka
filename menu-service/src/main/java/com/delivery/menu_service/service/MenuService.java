package com.delivery.menu_service.service;

import com.delivery.menu_service.dto.FindStoreByNameResponse;
import com.delivery.menu_service.dto.request.RegisterMenuRequest;
import com.delivery.menu_service.dto.request.UpdateMenuRequest;
import com.delivery.menu_service.dto.response.MenuResponse;
import com.delivery.menu_service.entity.MenuItem;
import com.delivery.menu_service.entity.MenuItemOption;
import com.delivery.menu_service.feign.StoreServiceFeignClient;
import com.delivery.menu_service.global.success.CommonResponse;
import com.delivery.menu_service.repository.MenuItemOptionRepository;
import com.delivery.menu_service.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional()
public class MenuService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemOptionRepository menuItemOptionRepository;
    private final StoreServiceFeignClient storeFeign;
    public MenuResponse RegisterMenu(RegisterMenuRequest registerMenuRequest) {
        if (menuItemRepository.findByName(registerMenuRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu item with name '" + registerMenuRequest.getName() + "' already exists.");
        }

        // Check if the store exists
        CommonResponse<FindStoreByNameResponse> storeResponse = storeFeign.getStoreById(registerMenuRequest.getStoreId());
        FindStoreByNameResponse store = storeResponse.data();
        if (store == null || store.getStatus() != FindStoreByNameResponse.StoreStatus.OPEN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store is clos  ed or does not exist.");
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
                .storeId(registerMenuRequest.getStoreId())
                .status(MenuItem.MenuItemStatus.AVAILABLE)
                .price(registerMenuRequest.getPrice())
                .menuItemOption(menuItemOption)
                .description(registerMenuRequest.getDescription())
                .build();
        menuItemRepository.save(menuItem);

        return MenuResponse.builder()
                .name(registerMenuRequest.getName())
                .itemId(menuItem.getItemId())
                .storeId(menuItem.getStoreId())
                .category(registerMenuRequest.getCategory())
                .price(registerMenuRequest.getPrice())
                .additionalPrice(registerMenuRequest.getAdditionalPrice())
                .status(menuItem.getStatus())
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
                .storeId(menuItem.getStoreId())
                .price(menuItem.getPrice())
                .status(menuItem.getStatus())
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
                .storeId(menuItem.getStoreId())
                .status(menuItem.getStatus())
                .price(menuItem.getPrice())
                .additionalPrice(menuItem.getMenuItemOption().getAdditionalPrice())
                .description(menuItem.getDescription())
                .build();
    }

    public MenuResponse updateMenuItem(Long itemId, UpdateMenuRequest updateMenuRequest) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found."));

        menuItem.updateInfo(updateMenuRequest.getName(), updateMenuRequest.getDescription(), updateMenuRequest.getPrice(), updateMenuRequest.getCategory());
        menuItemRepository.save(menuItem);

        return MenuResponse.builder()
                .name(menuItem.getName())
                .itemId(menuItem.getItemId())
                .category(menuItem.getCategory())
                .status(menuItem.getStatus())
                .price(menuItem.getPrice())
                .storeId(menuItem.getStoreId())
                .description(menuItem.getDescription())
                .build();
    }

    public void deleteMenuItem(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found."));
        menuItemRepository.delete(menuItem);
    }

    public List<MenuResponse> getMenuItemsByStore(Long storeId) {
        List<MenuItem> menuItems = menuItemRepository.findByStoreId(storeId);
        return menuItems.stream().map(item -> MenuResponse.builder()
                        .name(item.getName())
                        .itemId(item.getItemId())
                        .category(item.getCategory())
                        .price(item.getPrice())
                        .status(item.getStatus())
                        .storeId(item.getStoreId())
                        .description(item.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public MenuResponse changeMenuItemStatus(Long itemId) {
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found."));
        if (menuItem.getStatus() == MenuItem.MenuItemStatus.AVAILABLE) {
            menuItem.closeMenu();
        } else {
            menuItem.openMenu();
        }
        menuItemRepository.save(menuItem);

        return MenuResponse.builder()
                .name(menuItem.getName())
                .itemId(menuItem.getItemId())
                .category(menuItem.getCategory())
                .status(menuItem.getStatus())
                .storeId(menuItem.getStoreId())
                .price(menuItem.getPrice())
                .description(menuItem.getDescription())
                .status(menuItem.getStatus())
                .build();
    }
}
