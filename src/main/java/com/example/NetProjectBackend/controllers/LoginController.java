package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.UserRecovery;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, path="/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody UserRecovery userRecovery) {
        System.out.println("recovery");
        return userService.recovery(userRecovery.getEmail());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/code")
    public ResponseEntity<?> code(@RequestParam String param) {
        return userService.code(param);
    }
}
