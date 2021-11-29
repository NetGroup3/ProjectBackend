package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.userstock.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping(path = "/user/stock")
    public ResponseEntity<?> readStock(@RequestParam int userid) {
        return ResponseEntity.ok(userStockService.readStock(userid));
    }

    @DeleteMapping("/user/stock")
    public ResponseEntity<?> deleteStockElement(@RequestParam int stockid) {
        return ResponseEntity.ok(userStockService.deleteStockElement(stockid));
    }

    @PostMapping("/user/stock")
    public ResponseEntity<?> createStockElement(@RequestParam int userid,
                                                @RequestParam String ingredient,
                                                @RequestParam int amount) {
        return ResponseEntity.ok(userStockService.createStockElement(userid, ingredient, amount));
    }
}
