package com.delivery.user_service.repository;

import com.delivery.user_service.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findAuthorityByUserName(String name);
}
