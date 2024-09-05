package com.delivery.order_service.repository;

import com.delivery.order_service.entity.OrderItems;
import com.delivery.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrder(Orders order);
    List<OrderItems> findByMenuItem(Long menuItem);
    List<OrderItems> findByQuantity(int quantity);
    List<OrderItems> findByPrice(BigDecimal price);
}
