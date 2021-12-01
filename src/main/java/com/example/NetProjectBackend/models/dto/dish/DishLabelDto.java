package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishLabelDto {
    int id;
    int dish;
    int label;
}
