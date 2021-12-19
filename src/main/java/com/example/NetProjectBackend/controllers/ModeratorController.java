package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.UserListRequest;
import com.example.NetProjectBackend.models.dto.UserPaginatedDto;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
@CrossOrigin(origins = "*") 
@AllArgsConstructor
public class ModeratorController {

    private final UserService userService;
    /** Create only moderator, and send new random password to email */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        return ResponseEntity.ok(userService.createModerator(user));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateModerator(@RequestBody User user) {
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteModerator(@PathVariable int id) {
        userService.delete(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getModerators(@RequestBody UserListRequest req) {
        req.setSearchRole("moderator");
        List<UserPaginatedDto> list = userService.getAllSuitable(req);
        return ResponseEntity.ok(list);
    }
}
