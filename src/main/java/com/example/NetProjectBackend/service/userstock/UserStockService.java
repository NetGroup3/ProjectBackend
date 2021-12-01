package com.example.NetProjectBackend.service.userstock;

import com.example.NetProjectBackend.models.UserStockElement;

import java.util.List;

public interface UserStockService {
    List<UserStockElement> readStock(int userId, int limit, int offset);

    void deleteStockElement(int userId, int id);

    String createStockElement(int userId, String ingredient, int amount);

    String updateStockElement(int userId, String ingredient, int amount);

    UserStockElement readStockElement(int userId, String ingredient);
}
