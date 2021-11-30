package com.example.NetProjectBackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {
    int id;
    int userId;
    int dishId;
}
