package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;

import java.math.BigDecimal;
import java.util.List;

public interface DishDao {
    List<Dish> create(Dish dish);

    List<Dish> delete(int id);

    int setActive(int id, boolean active);

    List<DishIngredient> checkIngredient(int dishId, int ingredientId);

    List<DishIngredient> updateIngredient(int id, BigDecimal amount);

    List<DishIngredient> removeIngredient(int id);

    List<DishIngredient> createDishIngredient(DishIngredient dishIngredient);
}
