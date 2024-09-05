package com.delivery.user_service.config.security;

import com.delivery.user_service.entity.Authority;
import com.delivery.user_service.entity.User;
import com.delivery.user_service.repository.AuthorityRepository;
import com.delivery.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    //private final List<UserDetails> userDetails;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("name: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user can't found!")
        );
        log.info("findAuthorityByUserName: {}",authorityRepository.findAuthorityByUserName(user.getUsername()));
        List<Authority> authorityList =  authorityRepository.findAuthorityByUserName(user.getUsername());
        log.info("List<Authority>: {}", authorityList);
        user.setAuthorities(authorityRepository.findAuthorityByUserName(user.getUsername()));
        return new EntityUserDetails(user);
    }
}
