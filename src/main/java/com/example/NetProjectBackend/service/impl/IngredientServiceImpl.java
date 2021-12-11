package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.IngredientDao;
import com.example.NetProjectBackend.exeptions.NotFoundItemException;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;

    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    public int create(Ingredient ingredient) {
        ingredient.setActive(true);
        return ingredientDao.create(ingredient);
    }

    @Override
    public Ingredient read(int id) {
        return ingredientDao.read(id);
    }

    @Override
    public void update(Ingredient ingredient) {
        ingredientDao.update(ingredient);
    }

    @Override
    public void delete(int id) {
        if(ingredientDao.read(id)==null){
            throw new NotFoundItemException();
        }
        ingredientDao.delete(id);
    }

    @Override
    public List<Ingredient> readPage(int limit, int offset) {
        if (limit > 100) limit = 100;
        return ingredientDao.readPage(limit, offset);
    }

    @Override
    public List<Ingredient> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        if (limit > 100) limit = 100;
        return ingredientDao.readSearchPage(limit, offset, key, category, sortedBy);
    }
}
