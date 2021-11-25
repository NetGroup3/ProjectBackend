package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.UserListRequest;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        user.setRole(ERole.MODERATOR.getAuthority());
        return userService.createModerator(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateModerator(@RequestBody User user) {
        User userUpdated = userService.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteModerator(@PathVariable int id) {
        User userDeleted = userService.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @PostMapping("/moderators")
    public ResponseEntity<List<User>> getModerators(@RequestBody UserListRequest req) {
        req.setSearchRole("moderator");
        List<User> moderators = userService.getAllSuitable(req);
        if (moderators == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moderators);
    }
}
