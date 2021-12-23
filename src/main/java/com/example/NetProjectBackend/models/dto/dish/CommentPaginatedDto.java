package com.example.NetProjectBackend.models.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPaginatedDto {
    int id;
    Integer userId;
    String firstname;
    String lastname;
    String imageId;
    String text;
    String timestamp;
    int pagesTotal;

    public static CommentPaginatedDto transform(CommentPaginated comment) {
        if (comment == null) return null;

        CommentPaginatedDto newComment = new CommentPaginatedDto();
        newComment.setId(comment.getId());
        newComment.setUserId(comment.getUserId());
        newComment.setFirstname(comment.getFirstname());
        newComment.setLastname(comment.getLastname());
        newComment.setImageId(comment.getImageId());
        newComment.setText(comment.getText());
        newComment.setTimestamp(comment.getTimestamp().toString());
        newComment.setPagesTotal(comment.getPagesTotal());
        return newComment;
    }

    public static List<CommentPaginatedDto> transformList(List<CommentPaginated> list) {
        if (list == null) return null;

        List<CommentPaginatedDto> dtoList = new ArrayList<>();
        for (CommentPaginated comment : list) {
            dtoList.add(CommentPaginatedDto.transform(comment));
        }
        return dtoList;
    }
}
