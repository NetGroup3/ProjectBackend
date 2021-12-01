package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.MessageResponseDto;
import com.example.NetProjectBackend.models.dto.PasswordChangeRequestDto;
import com.example.NetProjectBackend.models.dto.UserImageDto;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        log.info("users_GET");
        User user = userServiceImpl.readById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        log.info("users_GET");
        User user = userServiceImpl.readByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userServiceImpl.create(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        log.info("users_PUT");
        User userUpdated = userServiceImpl.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        log.info("users_DELETE");
        User userDeleted = userServiceImpl.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        users = userServiceImpl.getAll();
        log.info(String.valueOf(users));
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChangeRequestDto passwordCR) {
        try {
            passwordCR.setUserId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            userServiceImpl.updatePassword(passwordCR);
            log.info("Password Changed");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Incorrect password");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Incorrect password"));
        }
    }

    @PutMapping("/personal-information")
    public void updatePersonalInformation(@RequestBody User userResponse) {
        log.info("good");
        User user = userServiceImpl.readById(userResponse.getId());
        user.setFirstname(userResponse.getFirstname());
        user.setLastname(userResponse.getLastname());
        userServiceImpl.update(user);
    }

    @PutMapping("/user-image")
    public void updateImage(@RequestBody UserImageDto response) {
        log.debug("Controller update user image");
        userServiceImpl.updateUserImage(response);
    }

}