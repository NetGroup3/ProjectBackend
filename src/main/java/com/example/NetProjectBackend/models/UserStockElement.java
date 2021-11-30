package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStockElement {
    int id;
    int userId;
    Ingredient ingredient;
    int amount;
}
