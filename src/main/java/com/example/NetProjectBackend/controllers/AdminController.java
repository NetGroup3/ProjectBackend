package com.example.NetProjectBackend.controllers;

import java.time.OffsetDateTime;
import java.util.List;

import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.UserListRequest;
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
        userCreated.setRole(ERole.MODERATOR.name());
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

    @PostMapping("/moderators")
    public ResponseEntity<List<User>> getModerators(@RequestBody UserListRequest req) {
        req.setSearchRole("moderator");
        List<User> moderators = userRepository.getAllSuitable(req);
        if (moderators == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moderators);
    }
}
