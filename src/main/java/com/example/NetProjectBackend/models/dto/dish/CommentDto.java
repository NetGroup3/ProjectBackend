package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    int id;
    int userId;
    String firstname;
    String lastname;
    String imageId;
    String text;
    OffsetDateTime timestamp;
}
