package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class userSettingsController {

    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.PATCH, path = "/personal-information")
    public ResponseEntity<?> updatePersonalInformation (@RequestBody User userResponse){
        User user = userRepository.readById(userResponse.getId());
        user.setFirstname(userResponse.getFirstname());
        user.setLastname(userResponse.getLastname());
        userRepository.update(user);
        return ResponseEntity.ok(200);
    }

}