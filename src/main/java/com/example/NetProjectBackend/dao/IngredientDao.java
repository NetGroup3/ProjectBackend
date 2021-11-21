package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Ingredient;

import java.util.List;

public interface IngredientDao {

    int create(Ingredient ingredient);

    Ingredient read(int id);

    void update(Ingredient ingredient);

    void delete(int id);

    List<Ingredient> readPage(int limit, int offset);
}
