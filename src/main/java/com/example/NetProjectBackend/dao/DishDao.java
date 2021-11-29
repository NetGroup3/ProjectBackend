package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.models.entity.Label;
import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Favourite;

import java.math.BigDecimal;
import java.util.List;

public interface DishDao {
    List<Dish> create(Dish dish);

    List<Dish> editDish(Dish dish);

    List<Dish> delete(int id);

    List<DishFormatDto> readList(DishSearchDto search, Integer userId);

    int setActive(int id, boolean active);

    DishFormatDto soloReadDish(int id, int userId);

    List<DishRecommendDto> getRecommend(int userId, int limit, int offset);

    List<Dish> getWithIngredients(String list, int limit, int offset);

    List<DishIngredientDto> checkIngredient(int dishId, int ingredientId);

    List<DishIngredientDto> updateIngredient(int id, BigDecimal amount);

    List<DishIngredientDto> removeIngredient(int id);

    List<DishIngredientDto> createDishIngredient(DishIngredientDto dishIngredientDto);

    List<Ingredient> readIngredientsRelation(int id);

    List<DishKitchenwareDto> checkKitchenware(DishKitchenwareDto dishKitchenwareDto);

    List<DishKitchenwareDto> removeKitchenware(int id);

    List<DishKitchenwareDto> createDishKitchenware(DishKitchenwareDto dishKitchenwareDto);

    List<Kitchenware> readKitchenwareRelation(int id);

    List<CommentDto> readCommentRelation(int id);

    List<Comment> createComment(Comment comment, int userId);

    List<Comment> deleteComment(int id, int userId);

    boolean checkFavourite(int dishId, int userId);

    boolean addFavourite(int userId, int dishId);

    List<Favourite> removeFavourite(int id, int userId);

    List<DishFormatDto> getFavourite(int userId);

    List<Label> readLabelRelation(int id);

    List<Label> createLabel(Label label);

    List<Label> getLabels(int limit, int offset);

    List<Label> editLabel(Label label);

    List<Label> deleteLabel(int id);

    List<DishLabelDto> addLabel(DishLabelDto label);

    List<DishLabelDto> removeLabel(int id);

    boolean setLike(int dishId);
}
