package com.example.NetProjectBackend.service.userstock;

import org.springframework.http.ResponseEntity;

public interface UserStockService {
    ResponseEntity<?> readStock(int userId);

    ResponseEntity<?> deleteStockElement(int stockid);

    ResponseEntity<?> createStockElement(int userId, String ingredient, int amount);

    ResponseEntity<?> updateStockElement(int userId, String ingredient, int amount);
}
