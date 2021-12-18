package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSessionService {

    public UserDetailsImpl getUserFromSession (){
        return ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    public int getUserIdFromSession (){
        return getUserFromSession().getId();
    }

}
