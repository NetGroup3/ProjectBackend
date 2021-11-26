package com.example.NetProjectBackend.service.userstock;

import org.springframework.http.ResponseEntity;

public interface UserStockService {
    ResponseEntity<?> readStock(int userId);

    ResponseEntity<?> deleteStockElement(int stockid);
}
