package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDish {
    private int id;
    private int userId;
    private int eventId;
    private int dishId;
    private int quantity;
}
