package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.PasswordChangeGroup;
import com.example.NetProjectBackend.models.dto.UserImage;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.readById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.readByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setRole(ERole.USER.getAuthority());
        return userService.create(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userUpdated = userService.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User userDeleted = userService.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        users = userService.getAll();
        System.out.println(users);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/change-password")
    public ResponseEntity<User> updatePassword(@RequestBody PasswordChangeGroup passwordCG) {
        try {
            userService.checkOldPassword(passwordCG);
            User userUpdatedPassword = userService.updatePassword(passwordCG.getPassword(), passwordCG.getUserId());
            return ResponseEntity.ok(userUpdatedPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/personal-information")
    public ResponseEntity<?> updatePersonalInformation (@RequestBody User userResponse){
        User user = userService.readById(userResponse.getId());
        user.setFirstname(userResponse.getFirstname());
        user.setLastname(userResponse.getLastname());
        userService.update(user);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/user-image")
    public void updateImage (@RequestBody UserImage response){
        System.out.println("logggggg");
        System.out.println(response.getId());
        System.out.println(response.getImageId());

        log.debug("Controller update user image");
        userService.updateUserImage(response);
    }

}