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

    List<DishIngredient> addIngredient (DishIngredient dishIngredient);

    List<DishIngredient> removeIngredient(int id);

    List<DishKitchenware> addKitchenware(DishKitchenware dishKitchenware);

    List<DishKitchenware> removeKitchenware(int id);

    List<DishFormat> readList(int limit, int page, boolean desc, String title, String category, Integer userId);

    DishView getDish(int id, Integer userId);

    List<DishRecommend> getRecommend(int userId, int limit, int page);

    List<Dish> getWithIngredients(List<Integer> values, int limit, int page);

    List<DishFormat> getFavourite(int userId);

    boolean addFavourite(int userId, int dishId);

    List<Favourite> removeFavourite(int userId, int dishId);

    boolean setLike(int dishId);

    List<Comment> createComment(Comment comment, int userId);

    List<Comment> deleteComment(int comment, int userId);

    List<Label> createLabel(Label label);

    List<Label> editLabel(Label label);

    List<Label> getLabels(int limit, int offset);

    List<Label> deleteLabel(int id);

    List<DishLabel> addLabel(DishLabel label);

    List<DishLabel> removeLabel(int id);
}
