package com.delivery.store_service.repository;


import com.delivery.store_service.entity.Store;
import com.delivery.store_service.entity.StoreCategory;
import com.delivery.store_service.entity.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);


    @Query("SELECT s FROM Store s WHERE " +
            "(:name IS NULL OR s.name LIKE %:name%) AND " +
            "(:address IS NULL OR s.address LIKE %:address%) AND " +
            "(:category IS NULL OR s.category = :category) AND " +
            "s.status = com.delivery.store_service.entity.StoreStatus.OPEN")
    List<Store> searchStores(@Param("name") String name,
                             @Param("address") String address,
                             @Param("category") StoreCategory category);
}
