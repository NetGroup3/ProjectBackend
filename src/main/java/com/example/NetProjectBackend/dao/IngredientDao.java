package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;

import java.util.List;

public interface IngredientDao {

    int create(Ingredient ingredient);

    Ingredient read(int id);

    void update(Ingredient ingredient);

    void delete(int id);

    List<Ingredient> readPage(int limit, int offset);

    List<Ingredient> readSearchPage(int limit, int offset, String key, String category, String sortedBy);
}
