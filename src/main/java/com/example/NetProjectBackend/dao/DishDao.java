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

    List<DishFormat> readList(DishSearch search, Integer userId);

    int setActive(int id, boolean active);

    DishFormat soloReadDish(int id, int userId);

    List<DishRecommend> getRecommend(int userId, int limit, int offset);

    List<Dish> getWithIngredients(String list, int limit, int offset);

    List<DishIngredient> checkIngredient(int dishId, int ingredientId);

    List<DishIngredient> updateIngredient(int id, BigDecimal amount);

    List<DishIngredient> removeIngredient(int id);

    List<DishIngredient> createDishIngredient(DishIngredient dishIngredient);

    List<Ingredient> readIngredientsRelation(int id);

    List<DishKitchenware> checkKitchenware(DishKitchenware dishKitchenware);

    List<DishKitchenware> removeKitchenware(int id);

    List<DishKitchenware> createDishKitchenware(DishKitchenware dishKitchenware);

    List<Kitchenware> readKitchenwareRelation(int id);

    List<CommentView> readCommentRelation(int id);

    List<Comment> createComment(Comment comment, int userId);

    List<Comment> deleteComment(int id, int userId);

    boolean checkFavourite(int dishId, int userId);

    boolean addFavourite(int userId, int dishId);

    List<Favourite> removeFavourite(int id, int userId);

    List<DishFormat> getFavourite(int userId);

    List<Label> readLabelRelation(int id);

    List<Label> createLabel(Label label);

    List<Label> getLabels(int limit, int offset);

    List<Label> editLabel(Label label);

    List<Label> deleteLabel(int id);

    List<DishLabel> addLabel(DishLabel label);

    List<DishLabel> removeLabel(int id);

    boolean setLike(int dishId);
}
