package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    int id;
    String title;
    String description;
    String category;
    String imageId;
    boolean active;
    String measurement;
}
