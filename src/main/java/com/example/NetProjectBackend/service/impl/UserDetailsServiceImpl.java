package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.readByEmail(email);
        return UserDetailsImpl.build(user);
    }
}
