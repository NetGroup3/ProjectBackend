package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.UserStockElement;
import com.example.NetProjectBackend.service.userstock.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stock")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping("/{userId}")
    public List<UserStockElement> readStock(@PathVariable int userId) {
            return userStockService.readStock(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteStockElement(@PathVariable int userId,
                                                @RequestParam String ingredient) {
        return ResponseEntity.ok(userStockService.deleteStockElement(userId, ingredient));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createStockElement(@PathVariable int userId,
                                                @RequestParam String ingredient,
                                                @RequestParam int amount) {
        return ResponseEntity.ok(userStockService.createStockElement(userId, ingredient, amount));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateStockElement(@PathVariable int userId,
                                                @RequestParam String ingredient,
                                                @RequestParam int amount) {
        return ResponseEntity.ok(userStockService.updateStockElement(userId, ingredient, amount));
    }
}
