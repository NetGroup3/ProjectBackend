package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.UserStockElement;

import java.util.List;

public interface UserStockService {
    List<UserStockElement> readStock(int userId, int limit, int offset);

    List<Ingredient> readIngredients(int userId, int limit, int offset);

    String deleteStockElement(int userId, int id);

    String createStockElement(int userId, int ingredientId, int amount);

    String updateStockElement(int userId, int ingredientId, int amount);

    UserStockElement readStockElement(int userId, int ingredientId);

    List<UserStockElement> readSearchPage(int limit, int offset, String key, String category, String sortedBy);

    int getPages(int limit);
}
