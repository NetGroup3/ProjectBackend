package com.example.NetProjectBackend.models.dto.dish;

import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishView {
    Dish dish;
    List<Ingredient> ingredients;
    List<Kitchenware> kitchenware;
    List<DishLabel> labels;
    List<DishComment> comments;
    boolean favourite;
    boolean like;
    int likes;
}
