package com.example.NetProjectBackend.models.dto.dish;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.models.entity.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishView {
    DishFormatDto dish;
    List<Ingredient> ingredients;
    List<Kitchenware> kitchenware;
    List<CommentDto> comments;
    List<Label> labels;
}
