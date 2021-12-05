package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.dto.UserListRequest;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.Paginator;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createModerator(@RequestBody User user) {
        user.setRole(ERole.MODERATOR.getAuthority());
        return ResponseEntity.ok(userServiceImpl.createModerator(user));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateModerator(@RequestBody User user) {
        UserDto userUpdated = userServiceImpl.update(user);
        if (userUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteModerator(@PathVariable int id) {
        UserDto userDeleted = userServiceImpl.delete(id);
        if (userDeleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDeleted);
    }

    @PostMapping("/moderators")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getModerators(@RequestBody UserListRequest req) {
        req.setSearchRole("moderator");
        Paginator.PaginatedResponse res = userServiceImpl.getAllSuitable(req);
        return ResponseEntity.ok(res);
    }
}
