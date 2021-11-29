package com.example.NetProjectBackend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello(){
        return "hello world!!";
    }
    @GetMapping("/hello/user")
    @PreAuthorize("hasAuthority('USER')")
    public String getUser(){
        return "USER HELLO";
    }

    @GetMapping("/hello/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public String getModerator(){
        return "MODERATOR HELLO";
    }
    @GetMapping("/hello/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin(){
        return "ADMIN HELLO";
    }
}
