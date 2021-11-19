package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.PasswordChangeGroup;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        System.out.println("users_GET");
        User user = userRepository.readById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        System.out.println("users_GET");
        User user = userRepository.readByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("users_POST");
        System.out.println("try to create user");
        System.out.println(user.toString());
        //move to @Service or elsewhere
        user.setTimestamp(OffsetDateTime.now());
        //

        User userCreated = userRepository.create(user);
        if (userCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userCreated);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("users_PUT");
        User userUpdated = userRepository.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        System.out.println("users_DELETE");
        User userDeleted = userRepository.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        users = userRepository.getAll();
        System.out.println(users);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/change-password")
    public ResponseEntity<User> updatePassword(@RequestBody PasswordChangeGroup passwordCG) {
        System.out.println("users_CHANGE_PASSWORD");
        try {
            userService.checkOldPassword(passwordCG);
            User userUpdatedPassword = userRepository.updatePassword(passwordCG.getPassword(), passwordCG.getUserId());
            return ResponseEntity.ok(userUpdatedPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
