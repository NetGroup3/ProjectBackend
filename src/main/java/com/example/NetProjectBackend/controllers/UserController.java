package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.MessageResponseDto;
import com.example.NetProjectBackend.models.dto.PasswordChangeRequestDto;
import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.dto.UserImageDto;
import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    //@RequestMapping(method = RequestMethod.POST)
    //public ResponseEntity<?> createUser(@RequestBody User user) {
    //    return ResponseEntity.ok(userServiceImpl.create(user));
    //}

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

    /*
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
    */

    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordChangeRequestDto passwordCR) {
        try {
            passwordCR.setUserId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            userServiceImpl.updatePassword(passwordCR);
            log.info("Password Changed");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Incorrect password");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Incorrect password"));
        }
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