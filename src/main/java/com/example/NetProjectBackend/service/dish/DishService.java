package com.example.NetProjectBackend.service.dish;

import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishService {
    List<Dish> createDish(Dish dish);

    List<Dish> deleteDish(int id);

    int setActive(int id, boolean active);

    ResponseEntity<?> addIngredient (DishIngredient dishIngredient);

    List<DishIngredient> removeIngredient(int id);
}
