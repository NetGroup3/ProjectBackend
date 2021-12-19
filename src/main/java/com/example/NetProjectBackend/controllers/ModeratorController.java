package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@CrossOrigin(origins = "*") 
@AllArgsConstructor
@Secured("ADMIN")
public class ModeratorController {

    private final UserService userService;

    /** Create only moderator, and send new random password to email */
    @PostMapping
    public void createModerator(@RequestBody User user) {
        userService.createModerator(user);
    }

    @PutMapping
    public void updateModerator(@RequestBody User user) {
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteModerator(@PathVariable int id) {
        userService.delete(id);
    }

}
