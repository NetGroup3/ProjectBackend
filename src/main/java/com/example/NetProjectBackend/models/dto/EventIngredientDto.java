package com.example.NetProjectBackend.models.dto;

import com.example.NetProjectBackend.models.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIngredientDto {
    Ingredient ingredient;
    double amount;
}
