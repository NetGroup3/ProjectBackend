package com.example.NetProjectBackend.service.dish;

import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.entity.Favourite;
import com.example.NetProjectBackend.models.entity.Label;

import java.util.List;

public interface DishService {
    List<Dish> createDish(Dish dish);

    List<Dish> editDish(Dish dish);

    List<Dish> deleteDish(int id);

    int setActive(int id, boolean active);

    List<DishIngredientDto> addIngredient (DishIngredientDto dishIngredientDto);

    List<DishIngredientDto> removeIngredient(int id);

    List<DishKitchenwareDto> addKitchenware(DishKitchenwareDto dishKitchenwareDto);

    List<DishKitchenwareDto> removeKitchenware(int id);

    List<DishFormatDto> readList(int limit, int page, boolean desc, String title, String category, Integer userId);

    DishDto getDish(int id, Integer userId);

    List<DishRecommendDto> getRecommend(int userId, int limit, int page);

    List<Dish> getWithIngredients(List<Integer> values, int limit, int page);

    List<DishFormatDto> getFavourite(int userId);

    boolean addFavourite(int userId, int dishId);

    List<Favourite> removeFavourite(int userId, int dishId);

    boolean setLike(int dishId);

    List<Comment> createComment(Comment comment, int userId);

    List<Comment> deleteComment(int comment, int userId);

    List<Label> createLabel(Label label);

    List<Label> editLabel(Label label);

    List<Label> getLabels(int limit, int offset);

    List<Label> deleteLabel(int id);

    List<DishLabelDto> addLabel(DishLabelDto label);

    List<DishLabelDto> removeLabel(int id);
}
