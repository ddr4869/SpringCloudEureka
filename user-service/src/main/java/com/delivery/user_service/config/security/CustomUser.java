package com.delivery.user_service.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUser implements UserDetails {
    private String id;	//계정의 아이디
    private String name;	//계정 사용자의 이름
    private String password;	//계정의 비밀번호
    private String authorities;	//계정이 가지고 있는 권한 목록

    public CustomUser(String id, String name, String password, String authorities) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(this.authorities));
        return auth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
