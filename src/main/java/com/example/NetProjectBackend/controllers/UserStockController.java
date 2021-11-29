package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.userstock.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping(path = "/{userId}/stock")
    public ResponseEntity<?> readStock(@PathVariable int userId) {
        return ResponseEntity.ok(userStockService.readStock(userId));
    }

    @DeleteMapping("/stock")
    public ResponseEntity<?> deleteStockElement(@RequestParam int stockId) {
        return ResponseEntity.ok(userStockService.deleteStockElement(stockId));
    }

    @PostMapping("/{userId}/stock")
    public ResponseEntity<?> createStockElement(@PathVariable int userId,
                                                @RequestParam String ingredient,
                                                @RequestParam int amount) {
        return ResponseEntity.ok(userStockService.createStockElement(userId, ingredient, amount));
    }

    @PatchMapping("/{userId}/stock")
    public ResponseEntity<?> updateStockElement(@PathVariable int userId,
                                                @RequestParam String ingredient,
                                                @RequestParam int amount) {
        return ResponseEntity.ok(userStockService.updateStockElement(userId, ingredient, amount));
    }
}
