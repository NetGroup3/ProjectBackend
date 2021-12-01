package com.example.NetProjectBackend.service.ingredient;

import com.example.NetProjectBackend.models.Ingredient;

import java.util.List;

public interface IngredientService {
    int create(Ingredient ingredient);

    Ingredient read(int id);

    void update(Ingredient ingredient);

    void delete(int id);

    List<Ingredient> readPage(int limit, int offset);

    List<Ingredient> readSearchPage(int limit, int offset, String key, String category, String sortedBy);
}
