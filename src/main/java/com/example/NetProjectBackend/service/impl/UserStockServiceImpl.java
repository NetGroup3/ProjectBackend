package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.UserStockDao;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.UserStockElement;
import com.example.NetProjectBackend.service.UserStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserStockServiceImpl implements UserStockService {
    private final UserStockDao userStockDao;

    public UserStockServiceImpl(UserStockDao userStockDao) {
        this.userStockDao = userStockDao;
    }

    @Override
    public List<UserStockElement> readStock(int userId, int limit, int offset) {
        return userStockDao.readStock(userId, limit, offset);
    }

    @Override
    public List<Ingredient> readIngredients(int userId, int limit, int offset) {
        return userStockDao.readIngredients(userId, limit, offset);
    }

    @Override
    public String deleteStockElement(int userId, int ingredientId) {
        if (userStockDao.readStockElement(userId, ingredientId) == null)
            return "Not found";
        userStockDao.deleteStockElement(userId, ingredientId);
        return "Ok";
    }

    @Override
    public String createStockElement(int userId, int ingredientId, int amount) {
        if (userStockDao.readStockElement(userId, ingredientId) != null)
            return "Already exist in your stock";
        userStockDao.createStockElement(userId, ingredientId, amount);
        return "Ok";
    }

    @Override
    public String updateStockElement(int userId, int ingredientId, int amount) {
        if (userStockDao.readStockElement(userId, ingredientId) == null)
            return "Not found";
        userStockDao.updateStockElement(userId, ingredientId, amount);
        return "Ok";
    }

    @Override
    public UserStockElement readStockElement(int userId, int ingredientId) {
        return userStockDao.readStockElement(userId, ingredientId);
    }

    @Override
    public List<UserStockElement> readSearchPage(int limit, int offset, String key, String category, String sortedBy, int userId) {
        return userStockDao.readSearchPage(limit, offset, key, category, sortedBy, userId);
    }
}
