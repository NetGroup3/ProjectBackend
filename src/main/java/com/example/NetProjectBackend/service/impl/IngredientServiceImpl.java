package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.IngredientDao;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;

    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    public ResponseEntity<?> create(Ingredient ingredient) {
        ingredient.setActive(true);
        return ResponseEntity.ok(ingredientDao.create(ingredient));
    }

    @Override
    public ResponseEntity<?> read(int id) {
        return ResponseEntity.ok(ingredientDao.read(id));
    }

    @Override
    public ResponseEntity<?> update(Ingredient ingredient) {
        ingredientDao.update(ingredient);
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        ingredientDao.delete(id);
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<?> readPage(int limit, int offset) {
        if (limit > 100) limit = 100;
        return ResponseEntity.ok(ingredientDao.readPage(limit, offset));
    }

    @Override
    public ResponseEntity<?> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        if (limit > 100) limit = 100;
        return ResponseEntity.ok(ingredientDao.readSearchPage(limit, offset, key, category, sortedBy));
    }
}
