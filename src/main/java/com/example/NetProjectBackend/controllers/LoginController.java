package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.service.UserService;
import com.example.NetProjectBackend.services.mail.Mail;
import com.example.NetProjectBackend.services.password.HashPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Objects;


@RestController
public class LoginController {

    private final Mail mail;
    private final UserRepository userRepository;
    private final UserService userService;


    LoginController(UserRepository userRepository, UserService userService, Mail mail) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mail = mail;
    }


    @GetMapping( path = "/login")
    public ResponseEntity<User> login(@RequestHeader(value = "Authorization") String authHeader) {
        System.out.println("login");
        System.out.println(authHeader);
        String decoded = new String(Base64.getDecoder().decode(authHeader));

        String[] subStr = decoded.split(":");
        User user = userRepository.readByEmail(subStr[0]);

        if(user == null){
            System.out.println("USER == NULL");
            return ResponseEntity.notFound().build();
        }

        /* We compare the hash of the password from a DB and the hash entered by the user */
        if(Objects.equals(user.getPassword(), HashPassword.getHashPassword(subStr[1]))){
            System.out.println("user login ok");
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, path="/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody String email) {
        return userService.recovery(email);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/code")
    public ResponseEntity<?> code(@RequestParam String param) {
        return userService.code(param);
    }
}
