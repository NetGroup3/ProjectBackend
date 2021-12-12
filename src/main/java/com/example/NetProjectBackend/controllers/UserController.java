package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.UserService;
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

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDto user = userService.readById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        UserDto user = userService.readByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        UserDto userUpdated = userService.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        UserDto userDeleted = userService.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @PutMapping("/change-password")
    public void updatePassword(@RequestBody PasswordChangeRequestDto passwordCR) {
        userService.updatePassword(passwordCR);
            log.info("Password Changed");
    }

    @PutMapping("/personal-information")
    public void updatePersonalInformation(@RequestBody User userData) {
        userService.update(userData);
    }

    @PutMapping("/user-image")
    public void updateImage(@RequestBody UserImageDto obj) {
        userService.updateUserImage(obj);
    }

    @GetMapping("/user-search")
    public List<UserSearchDto> searchUsers(@RequestParam String name) {
        return userService.searchUsers(name);
    }

    @GetMapping("/user-profile/{id}")
    public UserProfileDto searchUser(@PathVariable int id) {
        return userService.searchUser(id);
    }
}