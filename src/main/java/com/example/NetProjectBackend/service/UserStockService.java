package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.UserStockElement;

import java.util.List;

public interface UserStockService {
    List<UserStockElement> readStock(int userId, int limit, int offset);

    String deleteStockElement(int userId, int id);

    String createStockElement(int userId, int ingredientId, int amount);

    String updateStockElement(int userId, int ingredientId, int amount);

    UserStockElement readStockElement(int userId, int ingredientId);
}
