package com.example.NetProjectBackend.controllers;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @GetMapping("/")
    public String login(){
        System.out.println("login");
        return "LOGIN";
    }
}
