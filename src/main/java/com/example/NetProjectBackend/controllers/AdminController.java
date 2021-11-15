package com.example.NetProjectBackend.controllers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.websocket.server.PathParam;

import javax.websocket.server.PathParam;

import com.example.NetProjectBackend.models.ERole;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {               //add validation

    private final UserRepository userRepository;  //replace with @Service layer

    AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    @RequestMapping(method = RequestMethod.POST, path="/create")
    public ResponseEntity<User> createModerator(@RequestBody User user) {

        System.out.println("users_POST");
        System.out.println("try to create moderator");
        System.out.println(user.toString());
        //move to @Service or elsewhere
        user.setTimestamp(OffsetDateTime.now());
        //
        User userCreated = userRepository.create(user);
        userCreated.setRole(ERole.ROLE_MODERATOR.name());
        if (userCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userCreated);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> updateModerator(@RequestBody User user) {
        System.out.println("users_PUT");
        User userUpdated = userRepository.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteModerator(@PathVariable int id) {
        System.out.println("users_DELETE");
        User userDeleted = userRepository.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }
    @GetMapping("/get_moderators")
    public ResponseEntity<List<User>> getModerators() {
        List<User> users = new ArrayList<>();
        users = userRepository.getAll();
        List<User> moderators = new ArrayList<>();
        for (User user:users) {
            if(Objects.equals(user.getRole(), ERole.ROLE_MODERATOR.name())){
                moderators.add(user);
            }
        }
        System.out.println(moderators);
        if (moderators == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moderators);
    }
//    @GetMapping("/search")
//    public ResponseEntity<User> searchByName(@PathVariable String name) {
//        System.out.println("search moderator");
//        User moderator = userRepository.readByName(name);
//        if (moderator == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(moderator);
//    }


}
