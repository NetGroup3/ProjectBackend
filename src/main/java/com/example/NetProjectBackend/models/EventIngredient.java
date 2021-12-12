package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIngredient {
    private int id;
    private int user_id;
    private int event_id;
    private int ingredient_id;
    private double amount;
}
