package com.delivery.user_service.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class CustomGrantedAuthority implements GrantedAuthority {

    private Set<GrantedAuthority> authorities;	//계정이 가지고 있는 권한 목록

    public CustomGrantedAuthority(GrantedAuthority authorities) {
        this.authorities = new HashSet<>();
//        Set<GrantedAuthority> grantedAuthorities = Set<GrantedAuthority>();
//        this.authorities=authorities;
    }
    @Override
    public String getAuthority() {
        return "test";
    }
}
