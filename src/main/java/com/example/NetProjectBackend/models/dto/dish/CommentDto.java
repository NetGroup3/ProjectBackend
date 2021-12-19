package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    int id;
    Integer userId;
    String firstname;
    String lastname;
    String imageId;
    String text;
    OffsetDateTime timestamp;

    public CommentDto(int id, Integer userId, String firstname, String lastname, String imageId, String text, OffsetDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.imageId = imageId;
        this.text = text;
        this.timestamp = timestamp;
    }
}
