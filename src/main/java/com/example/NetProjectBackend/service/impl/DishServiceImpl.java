package com.example.NetProjectBackend.service.impl;


import com.example.NetProjectBackend.dao.DishDao;
import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Favourite;
import com.example.NetProjectBackend.models.entity.Label;
import com.example.NetProjectBackend.service.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DishServiceImpl  implements DishService {

    private final DishDao dishDao;

    @Override
    public List<Dish> createDish(Dish dish) {
        return dishDao.create(dish);
    }

    @Override
    public List<Dish> editDish(Dish dish) {
        return dishDao.editDish(dish);
    }

    @Override
    public List<Dish> deleteDish(int id) {
        return dishDao.delete(id);
    }

    @Override
    public int setActive(int id, boolean active) {
        return dishDao.setActive(id, active);
    }

    @Override
    public List<DishIngredientDto> addIngredient(DishIngredientDto dishIngredientDto) {
        List<DishIngredientDto> check = dishDao.checkIngredient(dishIngredientDto.getDish(), dishIngredientDto.getIngredient());
        if (check.size() > 0)
            return dishDao.updateIngredient(check.get(0).getId(), dishIngredientDto.getAmount());
        return dishDao.createDishIngredient(dishIngredientDto);

    }

    @Override
    public List<DishIngredientDto> removeIngredient(int id) {
        return dishDao.removeIngredient(id);
    }

    @Override
    public List<DishKitchenwareDto> addKitchenware(DishKitchenwareDto dishKitchenwareDto) {
        List<DishKitchenwareDto> kitchenware = dishDao.checkKitchenware(dishKitchenwareDto);
        if (kitchenware.size() > 1) return kitchenware;
        return dishDao.createDishKitchenware(dishKitchenwareDto);

    }

    @Override
    public List<DishKitchenwareDto> removeKitchenware(int id) {
        return dishDao.removeKitchenware(id);
    }

    @Override
    public List<DishFormatDto> readList(int limit, int page, boolean desc, String title, String category, Integer userId) {
        if (title == null) title = "%";
        else title = "%"+title+"%";
        if (category == null) category = "%";
        else category = "%"+category+"%";
        DishSearchDto search = new DishSearchDto(
                limit,
                page,
                limit*page,
                desc,
                title,
                category
        );
        return dishDao.readList(search, userId);
    }

    @Override
    public DishDto getDish(int id, Integer userId) {
        DishDto dishDto = new DishDto(
                dishDao.soloReadDish(id, userId),
                dishDao.readIngredientsRelation(id),
                dishDao.readKitchenwareRelation(id),
                dishDao.readCommentRelation(id),
                dishDao.readLabelRelation(id)
        );
        return dishDto;
    }

    @Override
    public List<DishRecommendDto> getRecommend(int userId, int limit, int page) {
        return dishDao.getRecommend(userId, limit, limit*page);
    }

    @Override
    public List<Dish> getWithIngredients(List<Integer> values, int limit, int page) {
        if (values.size() < 1) values.add(0);
        String list = "";
        for (int val : values) list += val + ",";
        return dishDao.getWithIngredients(list.substring(0, list.length() -1), limit, limit*page);
    }

    @Override
    public List<DishFormatDto> getFavourite (int userId) {
        return dishDao.getFavourite(userId);
    }

    @Override
    public boolean addFavourite(int userId, int dishId) {
        if (dishDao.checkFavourite(dishId, userId)) return true;
        return dishDao.addFavourite(userId, dishId);
    }

    @Override
    public List<Favourite> removeFavourite(int userId, int dishId) {
        return dishDao.removeFavourite(dishId, userId);
    }

    @Override
    public boolean setLike(int dishId) {
        return dishDao.setLike(dishId);
    }

    @Override
    public List<Comment> createComment (Comment comment, int userId) {
        return dishDao.createComment(comment, userId);
    }

    @Override
    public List<Comment> deleteComment(int comment, int userId) {
        return dishDao.deleteComment(comment, userId);
    }

    @Override
    public List<Label> createLabel(Label label) {
        return dishDao.createLabel(label);
    }

    @Override
    public List<Label> editLabel(Label label) {
        return dishDao.editLabel(label);
    }

    @Override
    public List<Label> getLabels(int limit, int page) {
        return dishDao.getLabels(limit, limit*page);
    }

    @Override
    public List<Label> deleteLabel(int id) {
        return dishDao.deleteLabel(id);
    }

    @Override
    public List<DishLabelDto> addLabel(DishLabelDto label) {
        return dishDao.addLabel(label);
    }

    @Override
    public List<DishLabelDto> removeLabel(int id) {
        return dishDao.removeLabel(id);
    }
}
