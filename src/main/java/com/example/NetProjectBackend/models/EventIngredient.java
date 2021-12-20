package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIngredient {
    private int id;
    private int userId;
    private int eventId;
    private int ingredientId;
    private double amount;
}
