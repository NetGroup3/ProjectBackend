package com.example.NetProjectBackend.service.userstock;

import org.springframework.http.ResponseEntity;

public interface UserStockService {
    ResponseEntity<?> readStock(int userId, int limit, int offset);

    ResponseEntity<?> deleteStockElement(int userId, String ingredient);

    ResponseEntity<?> createStockElement(int userId, String ingredient, int amount);

    ResponseEntity<?> updateStockElement(int userId, String ingredient, int amount);
}
