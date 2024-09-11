package com.delivery.order_service.repository;


import com.delivery.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
    List<Orders> findByStoreId(Long storeId);
}
