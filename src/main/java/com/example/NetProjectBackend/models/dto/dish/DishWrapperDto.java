package com.example.NetProjectBackend.models.dto.dish;

import com.example.NetProjectBackend.models.entity.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishWrapperDto {
    Dish dish;
    List<DishIngredientDto> ingredients;
    List<Integer> kitchenware;
    List<Integer> label;
}
