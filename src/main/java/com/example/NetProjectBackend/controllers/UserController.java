package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDto user = userServiceImpl.readById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        UserDto user = userServiceImpl.readByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        UserDto userUpdated = userServiceImpl.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        UserDto userDeleted = userServiceImpl.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @PutMapping("/change-password")
    public void updatePassword(@RequestBody PasswordChangeRequestDto passwordCR) {
            userServiceImpl.updatePassword(passwordCR);
            log.info("Password Changed");
    }

    @PutMapping("/personal-information")
    public void updatePersonalInformation(@RequestBody User userData) {
        userServiceImpl.update(userData);
    }

    @PutMapping("/user-image")
    public void updateImage(@RequestBody UserImageDto obj) {
        userServiceImpl.updateUserImage(obj);
    }

    @GetMapping("/user-search")
    public List<UserSearchDto> searchUsers(@RequestParam String name) {
        return userServiceImpl.searchUsers(name);
    }

    @GetMapping("/user-profile/{id}")
    public UserProfileDto searchUser(@PathVariable int id) {
        return userServiceImpl.searchUser(id);
    }
}