package com.example.NetProjectBackend.service.userstock;

import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.dao.UserStockDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserStockServiceImpl implements UserStockService{
    private final UserStockDao userStockDao;

    public UserStockServiceImpl(UserStockDao userStockDao) {
        this.userStockDao = userStockDao;
    }


    @Override
    public ResponseEntity<?> readStock(int userId) {
        return ResponseEntity.ok(userStockDao.readStock(userId));
    }
}
