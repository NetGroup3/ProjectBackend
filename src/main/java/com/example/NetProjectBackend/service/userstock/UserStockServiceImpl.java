package com.example.NetProjectBackend.service.userstock;

import com.example.NetProjectBackend.dao.UserStockDao;
import com.example.NetProjectBackend.models.UserStockElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserStockServiceImpl implements UserStockService{
    private final UserStockDao userStockDao;

    public UserStockServiceImpl(UserStockDao userStockDao) {
        this.userStockDao = userStockDao;
    }


    @Override
    public List<UserStockElement> readStock(int userId, int limit, int offset) {
        return userStockDao.readStock(userId, limit, offset);
    }

    @Override
    public void deleteStockElement(int userId, int id) {
        userStockDao.deleteStockElement(userId, id);
    }

    @Override
    public String createStockElement(int userId, String ingredient, int amount) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        if (ingredientId == -1)
            return "Not found ingredient";
        if (userStockDao.readStockElement(userId, ingredientId) != null)
            return "Already exist in your stock";
        userStockDao.createStockElement(userId, ingredientId, amount);
        return "Ok";
    }

    @Override
    public String updateStockElement(int userId, String ingredient, int amount) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        if (userStockDao.readStockElement(userId, ingredientId) == null)
            return "Not found in your stock";
        userStockDao.updateStockElement(userId, ingredientId, amount);
        return "Ok";
    }

    @Override
    public UserStockElement readStockElement(int userId, String ingredient) {
        int ingredientId = userStockDao.ingredientExist(ingredient);
        return userStockDao.readStockElement(userId, ingredientId);
    }
}
