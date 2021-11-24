package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> readModerator(@PathVariable int id) {
        return ResponseEntity.ok(userService.readById(id));
    }

    @PostMapping
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        userService.createModerator(user);
        return ResponseEntity.ok(200);
    }

    @PutMapping
    public ResponseEntity<?> updateModerator(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteModerator(@RequestParam int id) {
        userService.delete(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping()
    public ResponseEntity<?> readModeratorPage(@RequestParam int limit, @RequestParam int offset) {
        return ResponseEntity.ok(userService.readPage(limit, offset));
    }

}
