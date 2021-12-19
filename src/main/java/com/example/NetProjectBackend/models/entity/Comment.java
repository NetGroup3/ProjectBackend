package com.example.NetProjectBackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    Integer id;
    Integer userId;
    Integer dishId;
    String text;
    OffsetDateTime timestamp;
}
