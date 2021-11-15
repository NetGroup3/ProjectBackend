package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.services.mail.Mail;
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

        String[] subStr;
        subStr = decoded.split(":");
        System.out.println(subStr);
        User user = userRepository.readByEmail(subStr[0]);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        else if(Objects.equals(user.getPassword(), subStr[1])){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, path="/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
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
        mail.confirmationCode("https://ourproject.space/code?param=", user.getEmail());
        return ResponseEntity.ok(userCreated);
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<Integer> recovery(@RequestHeader(value = "Authorization") String email) {
        System.out.println("recovery");

        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("Authorization", email);
        System.out.println(email);
        String decoded = new String(Base64.getDecoder().decode(email));

        if(userRepository.readByEmail(email) == null){ //проверка на ниличие в бд
            return ResponseEntity.notFound().build();
        } else {
            if (!mail.recoveryCode("https://ourproject.space/code?param=", email))
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(200);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/code")
    public ResponseEntity<Integer> code(@RequestParam String param) {
        Verify verify = mail.readByCode(param);
        if (verify == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userRepository.readById(verify.getUserId());
        if (Objects.equals(user.getStatus(), EStatus.ACTIVE.getAuthority())) {
            String newPassword = userRepository.randomPassword();
            if (mail.sendNewPassword("https://ourproject.space/code?param=", newPassword, user, verify)) {
                userRepository.changePassword(user, newPassword);
            } else {
                mail.confirmationCode("https://ourproject.space/code?param=", user.getEmail());
            }
        } else {
            userRepository.changeStatus(EStatus.ACTIVE, user.getId());
        }
        mail.deleteCode(verify.getUserId());
        return ResponseEntity.ok(200);
    }
}
