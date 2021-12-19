package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPaginated extends CommentDto{
    int pagesTotal;

    public CommentPaginated(int id,
                            Integer userId,
                            String firstname,
                            String lastname,
                            String imageId,
                            String text,
                            OffsetDateTime timestamp,
                            int pagesTotal) {
        super(id, userId, firstname, lastname, imageId, text, timestamp);
        this.pagesTotal = pagesTotal;
    }
}
