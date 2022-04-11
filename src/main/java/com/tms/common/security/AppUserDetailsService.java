package com.tms.common.security;

import com.tms.common.domain.UserEntity;
import com.tms.common.repository.UserRepository;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new AppUserDetails(findUserByEmail(s));
    }

    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
