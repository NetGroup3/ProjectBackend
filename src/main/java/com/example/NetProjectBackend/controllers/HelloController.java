package com.example.NetProjectBackend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@PreAuthorize("hasAuthority('USER')")
public class HelloController {

    @GetMapping
    public String getHello(){
        return "hello world!!";
    }
    @GetMapping("/user")
    public String getUser(){
        return "USER HELLO";
    }
}
