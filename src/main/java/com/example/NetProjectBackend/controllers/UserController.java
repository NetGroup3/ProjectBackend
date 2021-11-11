package com.example.NetProjectBackend.controllers;

import java.time.OffsetDateTime;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {               //add validation

    private final UserRepository userRepository;  //replace with @Service layer

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        System.out.println("users_GET");
        User user = userRepository.read(id);

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

    @RequestMapping(method = RequestMethod.GET, path = "/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {

        List<User> users = userRepository.getAll();
        User findUser;
        for (User user: users){
            if(user.contains(email)){
                findUser = user;
            }
        }

        if (findUser == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("link for recovery password");
        return ResponseEntity.ok(email);
    }



}
