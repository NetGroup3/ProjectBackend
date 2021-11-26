package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.userstock.UserStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping(path = "/user-stock")
    public ResponseEntity<?> readStock( int userId) {
        return ResponseEntity.ok(userStockService.readStock(userId));
    }
}
