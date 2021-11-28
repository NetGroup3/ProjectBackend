package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {

    private final UserService userService;
    /** Create only moderator, and send new random password to email */
    @PostMapping
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        return userService.createModerator(user);
    }

    @PutMapping
    public ResponseEntity<?> updateModerator(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModerator(@RequestParam int id) {
        userService.delete(id);
        return ResponseEntity.ok(200);
    }

    /** Return Arrays of moderator where limit it is size and offset it is start point */
    @GetMapping("/page")
    public ResponseEntity<?> readModeratorPage(@RequestParam int limit, @RequestParam int offset) {
        return ResponseEntity.ok(userService.readPage(limit, offset, ERole.MODERATOR.name()));
    }

}
