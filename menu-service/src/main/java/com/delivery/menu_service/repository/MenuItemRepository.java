package com.delivery.menu_service.repository;


import com.delivery.menu_service.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByName(String name);
    List<MenuItem> findByStoreId(Long storeId);
}
