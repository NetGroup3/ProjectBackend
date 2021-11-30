package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishFormat {
    int id;
    String title;
    String description;
    String category;
    String receipt;
    String imageId;
    boolean active;
    int likes;
    boolean favourite;
}
