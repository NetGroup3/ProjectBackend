package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.UserStockElement;

import java.util.List;


public interface UserStockDao {
    List<UserStockElement> readStock(int userId);

    void deleteStockElement(int stockid);
}
