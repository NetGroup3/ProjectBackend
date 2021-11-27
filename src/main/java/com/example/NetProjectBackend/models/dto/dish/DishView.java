package com.example.NetProjectBackend.models.dto.dish;

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
    int id;
    String title;
    String description;
    String category;
    String receipt;
    String imageId;
    boolean active;

    List<Ingredient> ingredients;
    List<Kitchenware> kitchenware;
    List<DishLabel> labels;
}
