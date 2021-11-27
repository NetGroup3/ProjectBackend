package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.dao.DishDao;
import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;
import com.example.NetProjectBackend.models.dto.dish.DishKitchenware;
import com.example.NetProjectBackend.models.dto.dish.DishSearch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class DishDaoImpl implements DishDao {

    private final JdbcTemplate jdbcTemplate;

    private static Dish mapDishRow(ResultSet rs, int rowNum) throws SQLException {
        return new Dish(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("receipt"),
                rs.getString("image_id"),
                rs.getBoolean("is_active")
        );
    }

    private static DishIngredient mapRelationIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new DishIngredient(
                rs.getInt("id"),
                rs.getInt("dish_id"),
                rs.getInt("ingredient_id"),
                rs.getBigDecimal("ingredient_amount")
        );
    }

    private static DishKitchenware mapRelationKitchenware(ResultSet rs, int rowNum) throws SQLException {
        return new DishKitchenware(
                rs.getInt("id"),
                rs.getInt("dish_id"),
                rs.getInt("kitchenware_id")
        );
    }

    //Dish
    @Override
    public List<Dish> create(Dish dish) {
        return jdbcTemplate.query("INSERT INTO DISH (title, description, category, receipt, image_id, is_active) VALUES (?, ?, ?, ?, ?, ?) RETURNING id, title, description, category, receipt, image_id, is_active",
                DishDaoImpl::mapDishRow,
                dish.getTitle(),
                dish.getDescription(),
                dish.getCategory(),
                dish.getReceipt(),
                dish.getImageId(),
                dish.isActive()
        );

    }


    @Override
    public List<Dish> delete(int id) {
        return jdbcTemplate.query("DELETE FROM DISH WHERE id = ? RETURNING id, title, description, category, receipt, image_id, is_active", DishDaoImpl::mapDishRow, id);
    }

    @Override
    public List<Dish> readList(DishSearch search) {
        if (search.getTitle() == null && search.getCategory() == null) {
            if (search.isDesc()) return jdbcTemplate.query("SELECT id, title, description, category, receipt, image_id, is_active FROM DISH ORDER BY title DESC LIMIT ? OFFSET ?", DishDaoImpl::mapDishRow, search.getLimit(), search.getTitle());
            return jdbcTemplate.query("SELECT id, title, description, category, receipt, image_id, is_active FROM DISH ORDER BY title ASC LIMIT ? OFFSET ?", DishDaoImpl::mapDishRow, search.getLimit(), search.getTitle());
        }
        if (search.isDesc()) return jdbcTemplate.query("SELECT id, title, description, category, receipt, image_id, is_active FROM DISH WHERE UPPER(title) LIKE UPPER(?) and UPPER(category) LIKE UPPER(?) ORDER BY title DESC LIMIT ? OFFSET ?", DishDaoImpl::mapDishRow, search.getTitle(), search.getCategory(), search.getLimit(), search.getOffset());
        return jdbcTemplate.query("SELECT id, title, description, category, receipt, image_id, is_active FROM DISH WHERE UPPER(title) LIKE UPPER(?) and UPPER(category) LIKE UPPER(?) ORDER BY title ASC LIMIT ? OFFSET ? ", DishDaoImpl::mapDishRow, search.getTitle(), search.getCategory(), search.getLimit(), search.getOffset());
    }

    @Override
    public int setActive(int id, boolean active) {
        return jdbcTemplate.update("UPDATE DISH SET is_active = ? WHERE id = ?", active, id);
    }


    //Dish Ingredient
    @Override
    public List<DishIngredient> checkIngredient(int dishId, int ingredientId) {
        return jdbcTemplate.query("SELECT id, dish_id, ingredient_id, ingredient_amount FROM DISH_INGREDIENT WHERE dish_id = ? and ingredient_id = ?", DishDaoImpl::mapRelationIngredient, dishId, ingredientId);
    }

    @Override
    public List<DishIngredient> updateIngredient(int id, BigDecimal amount) {
        return jdbcTemplate.query("UPDATE DISH_INGREDIENT SET ingredient_amount = ? WHERE id = ? RETURNING id, dish_id, ingredient_id, ingredient_amount", DishDaoImpl::mapRelationIngredient, amount, id);
    }

    @Override
    public List<DishIngredient> removeIngredient(int id) {
        return jdbcTemplate.query("DELETE FROM DISH_INGREDIENT WHERE id = ? RETURNING id, dish_id, ingredient_id, ingredient_amount", DishDaoImpl::mapRelationIngredient, id);
    }

    @Override
    public List<DishIngredient> createDishIngredient(DishIngredient dishIngredient) {
        return jdbcTemplate.query("INSERT INTO DISH_INGREDIENT (dish_id, ingredient_id, ingredient_amount) VALUES (?, ?, ?) RETURNING id, dish_id, ingredient_id, ingredient_amount",
                DishDaoImpl::mapRelationIngredient,
                dishIngredient.getDish(),
                dishIngredient.getIngredient(),
                dishIngredient.getAmount()
        );

    }

    //Dish Kitchenware
    @Override
    public List<DishKitchenware> checkKitchenware(DishKitchenware dishKitchenware) {
        return jdbcTemplate.query("SELECT id, dish_id, kitchenware_id FROM DISH_KITCHENWARE WHERE dish_id = ? and kitchenware_id = ?", DishDaoImpl::mapRelationKitchenware, dishKitchenware.getDish(), dishKitchenware.getKitchenware());
    }

    @Override
    public List<DishKitchenware> removeKitchenware(int id) {
        return jdbcTemplate.query("DELETE FROM DISH_KITCHENWARE WHERE id = ? RETURNING id, dish_id, kitchenware_id", DishDaoImpl::mapRelationKitchenware, id);
    }

    @Override
    public List<DishKitchenware> createDishKitchenware(DishKitchenware dishKitchenware) {
        return jdbcTemplate.query("INSERT INTO DISH_KITCHENWARE (dish_id, kitchenware_id) VALUES (?, ?) RETURNING id, dish_id, kitchenware_id",
                DishDaoImpl::mapRelationKitchenware,
                dishKitchenware.getDish(),
                dishKitchenware.getKitchenware()
        );

    }

}
