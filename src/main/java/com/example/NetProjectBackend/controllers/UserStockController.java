package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.userstock.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping(path = "/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readStock(@RequestParam int limit,
                                       @RequestParam int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.readStock(userId, limit, limit * page));
    }

    @DeleteMapping(path = "/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteStockElement(@RequestParam String ingredient) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.deleteStockElement(userId, ingredient));
    }

    @PostMapping("/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createStockElement(@RequestParam String ingredient,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.createStockElement(userId, ingredient, amount));
    }

    @PatchMapping("/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateStockElement(@RequestParam String ingredient,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.updateStockElement(userId, ingredient, amount));
    }
}
