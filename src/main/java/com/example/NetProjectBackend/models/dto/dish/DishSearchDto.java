package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishSearchDto {
    int limit;
    int page;
    int offset;
    boolean desc;
    String title;
    String category;
}
