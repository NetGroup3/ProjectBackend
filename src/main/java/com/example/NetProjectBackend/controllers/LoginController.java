package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.services.mail.Mail;
import com.example.NetProjectBackend.services.password.HashPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
public class LoginController {

    private final Mail mail;
    private final UserRepository userRepository;

    LoginController(UserRepository userRepository, Mail mail) {
        this.mail = mail;
        this.userRepository = userRepository;
    }


    @GetMapping( path = "/login")
    public ResponseEntity<User> login(@RequestHeader(value = "Authorization") String authHeader) {
        System.out.println("login");
        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("Authorization", authHeader);
        System.out.println(authHeader);
        String decoded = new String(Base64.getDecoder().decode(authHeader));

        String[] subStr = decoded.split(":");
        User user = userRepository.readByEmail(subStr[0]);

        if(user == null){
            System.out.println("USER == NULL");
            return ResponseEntity.notFound().build();
        }

        /** We compare the hash of the password from a DB and the hash entered by the user */
        if(Objects.equals(user.getPassword(), HashPassword.getHashPassword(subStr[1]))){
            System.out.println("user login ok");
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, path="/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        System.out.println(user.toString());
        //move to @Service or elsewhere
        user.setTimestamp(OffsetDateTime.now());

        if(userRepository.readByEmail(user.getEmail())!=null){ //есть ли пользователь с таким имейлом?
            return ResponseEntity.badRequest().build();
        }
        User userCreated = userRepository.create(user);
        if (userCreated == null) {
            return ResponseEntity.badRequest().build(); // если юзер по каким-то причинам не создался
        }
        System.out.println("send mail");
        mail.sendCode("https://ourproject.space/use_code?code=", "308ty397f239uopdh3f9p823dh928dhp1280dfh89ph", user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recovery(@RequestBody String email) {

        System.out.println("recovery");
        System.out.println(email);

        if(userRepository.readByEmail(email) == null){ //проверка на ниличие в бд
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("send mail");
            mail.sendCode("https://ourproject.space/use_code?code=", "308ty397f239uopdh3f9p823dh928dhp1280dfh89ph", email);
        }
        return ResponseEntity.noContent().build();
    }
}
