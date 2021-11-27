package com.example.NetProjectBackend.service.dish;

import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;
import com.example.NetProjectBackend.models.dto.dish.DishKitchenware;
import com.example.NetProjectBackend.models.dto.dish.DishSearch;

import java.util.List;

public interface DishService {
    List<Dish> createDish(Dish dish);

    List<Dish> deleteDish(int id);

    int setActive(int id, boolean active);

    List<DishIngredient> addIngredient (DishIngredient dishIngredient);

    List<DishIngredient> removeIngredient(int id);

    List<DishKitchenware> addKitchenware(DishKitchenware dishKitchenware);

    List<DishKitchenware> removeKitchenware(int id);

    List<Dish> readList(int limit, int page, boolean desc, String title, String category);
}
