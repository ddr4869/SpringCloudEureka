package com.delivery.menu_service.repository;

import com.delivery.menu_service.entity.MenuItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemOptionRepository extends JpaRepository<MenuItemOption, Long> {
}
