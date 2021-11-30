package com.example.NetProjectBackend.service.userstock;

import com.example.NetProjectBackend.models.UserStockElement;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserStockService {
    List<UserStockElement> readStock(int userId, int limit, int offset);

    ResponseEntity<?> deleteStockElement(int userId, String ingredient);

    ResponseEntity<?> createStockElement(int userId, String ingredient, int amount);

    ResponseEntity<?> updateStockElement(int userId, String ingredient, int amount);
}
