package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestBody User user){
        System.out.println("dsdfsdfc");
        System.out.println("login");
        return "LOGIN";
    }
}
