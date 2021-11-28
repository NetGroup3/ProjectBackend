package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.DishConfig;
import com.example.NetProjectBackend.dao.DishDao;
import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.entity.Favourite;
import com.example.NetProjectBackend.models.entity.Label;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class DishDaoImpl implements DishDao {

    private final JdbcTemplate jdbcTemplate;
    private final DishConfig q;

    private static Dish mapDishRow(ResultSet rs, int rowNum) throws SQLException {
        return new Dish(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("receipt"),
                rs.getString("image_id"),
                rs.getBoolean("is_active"),
                rs.getInt("likes")
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

    private static Ingredient mapIngredientRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("image_id"),
                rs.getBoolean("is_active"),
                rs.getString("measurement")
        );
    }

    private static Kitchenware mapKitchenwareRow(ResultSet rs, int rowNum) throws SQLException {
        return new Kitchenware(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("image_id"),
                rs.getBoolean("is_active")
        );
    }

    private static CommentView mapCommentViewRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommentView(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("image_id"),
                rs.getString("text"),
                rs.getObject("timestamp", OffsetDateTime.class)
        );
    }

    private static Comment mapCommentRow(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("dish_id"),
                rs.getString("text"),
                rs.getObject("timestamp", OffsetDateTime.class)
        );
    }

    private static Favourite mapFavouriteRow(ResultSet rs, int rowNum) throws SQLException {
        return new Favourite(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("dish_id")
        );
    }

    private static Label mapLabelRow(ResultSet rs, int rowNum) throws SQLException {
        return new Label(
                rs.getInt("id"),
                rs.getString("title")
        );
    }

    private static DishLabel mapDishLabelRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishLabel(
                rs.getInt("id"),
                rs.getInt("dish_id"),
                rs.getInt("label_id")
        );
    }

    private static DishFormat mapDishFormatRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishFormat(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("receipt"),
                rs.getString("image_id"),
                rs.getBoolean("is_active"),
                rs.getInt("likes"),
                rs.getBoolean("is_favourite")
        );
    }

    private static DishRecommend mapDishRecommendRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishRecommend(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("receipt"),
                rs.getString("image_id"),
                rs.getBoolean("is_active"),
                rs.getInt("likes"),
                rs.getInt("count"),
                rs.getBoolean("is_favourite")
        );
    }

    //Dish
    @Override
    public List<Dish> create(Dish dish) {
        return jdbcTemplate.query("INSERT INTO DISH (title, description, category, receipt, image_id, is_active) VALUES (?, ?, ?, ?, ?, ?) RETURNING id, title, description, category, receipt, image_id, is_active, likes",
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
    public List<Dish> editDish(Dish dish) {
        return jdbcTemplate.query("UPDATE DISH SET title = ?, description = ?, category = ?, receipt = ?, image_id = ?, is_active = ?, likes = ? WHERE id = ? RETURNING id, title, description, category, receipt, image_id, is_active, likes",
                DishDaoImpl::mapDishRow,
                dish.getTitle(),
                dish.getDescription(),
                dish.getCategory(),
                dish.getReceipt(),
                dish.getImageId(),
                dish.isActive(),
                dish.getLikes(),
                dish.getId()
        );
    }


    @Override
    public List<Dish> delete(int id) {
        return jdbcTemplate.query("DELETE FROM DISH WHERE id = ? RETURNING id, title, description, category, receipt, image_id, is_active", DishDaoImpl::mapDishRow, id);
    }

    @Override
    public List<DishFormat> readList(DishSearch search, Integer userId) {
        if (search.getTitle() == null && search.getCategory() == null) {
            if (search.isDesc()) return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? ORDER BY title DESC LIMIT ? OFFSET ?", DishDaoImpl::mapDishFormatRow, userId, search.getLimit(), search.getTitle());
            return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? ORDER BY title ASC LIMIT ? OFFSET ?", DishDaoImpl::mapDishFormatRow, userId, search.getLimit(), search.getTitle());
        }
        if (search.isDesc()) return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? WHERE UPPER(title) LIKE UPPER(?) and UPPER(category) LIKE UPPER(?) ORDER BY title DESC LIMIT ? OFFSET ?", DishDaoImpl::mapDishFormatRow, userId, search.getTitle(), search.getCategory(), search.getLimit(), search.getOffset());
        return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? WHERE UPPER(title) LIKE UPPER(?) and UPPER(category) LIKE UPPER(?) ORDER BY title ASC LIMIT ? OFFSET ?", DishDaoImpl::mapDishFormatRow, userId, search.getTitle(), search.getCategory(), search.getLimit(), search.getOffset());
    }

    @Override
    public int setActive(int id, boolean active) {
        return jdbcTemplate.update("UPDATE DISH SET is_active = ? WHERE id = ?", active, id);
    }

    @Override
    public DishFormat soloReadDish(int id, int userId) {
        return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes,  CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? WHERE dish.id = ?", DishDaoImpl::mapDishFormatRow, userId, id).get(0);
    }

    @Override
    public List<DishRecommend> getRecommend(int userId, int limit, int offset) {
        return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, count.count, CASE WHEN favourite.id IS NULL THEN false ELSE true END AS is_favourite FROM ( SELECT dish_ingredient.dish_id, count(dish_ingredient.dish_id) AS count FROM dish_ingredient INNER JOIN stock ON dish_ingredient.dish_id=stock.ingredient_id WHERE stock.user_id = ? GROUP BY dish_ingredient.dish_id ORDER BY count DESC LIMIT ? OFFSET ? ) AS count, dish LEFT JOIN favourite ON favourite.dish_id=dish.id AND favourite.user_id = ? WHERE dish.id=count.dish_id ORDER BY count DESC", DishDaoImpl::mapDishRecommendRow, userId, limit, offset, userId);
    }

    @Override
    public List<Dish> getWithIngredients(String list, int limit, int offset) {
        return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes FROM dish INNER JOIN dish_ingredient ON dish_ingredient.ingredient_id IN ("+ list +") and dish.id=dish_ingredient.dish_id GROUP BY dish.id ORDER BY count(dish_ingredient.id) DESC LIMIT ? OFFSET ?", DishDaoImpl::mapDishRow, limit, offset);
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

    @Override
    public List<Ingredient> readIngredientsRelation(int id) {
        return jdbcTemplate.query("SELECT ingredient.id, ingredient.title, ingredient.description, ingredient.category, ingredient.image_id, ingredient.is_active, ingredient.measurement FROM ingredient LEFT JOIN dish_ingredient ON ingredient.id=dish_ingredient.ingredient_id WHERE dish_id=?", DishDaoImpl::mapIngredientRow, id);
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

    @Override
    public List<Kitchenware> readKitchenwareRelation(int id) {
        return jdbcTemplate.query("SELECT kitchenware.id, kitchenware.title, kitchenware.description, kitchenware.category, kitchenware.image_id, kitchenware.is_active FROM kitchenware LEFT JOIN dish_kitchenware ON kitchenware.id=dish_kitchenware.kitchenware_id WHERE dish_id = ?", DishDaoImpl::mapKitchenwareRow, id);
    }

    //Comment
    @Override
    public List<CommentView> readCommentRelation(int id) {
        return jdbcTemplate.query("SELECT comment.id AS id, client.id AS user_id, client.first_name, client.last_name, client.image_id, comment.text, comment.timestamp FROM client LEFT JOIN comment ON client.id=comment.user_id WHERE comment.dish_id = ?", DishDaoImpl::mapCommentViewRow, id);
    }

    @Override
    public List<Comment> createComment(Comment comment, int userId) {
        List<Comment> check = jdbcTemplate.query("SELECT id, user_id, dish_id, text, timestamp FROM COMMENT WHERE user_id = ? and dish_id = ?",
                DishDaoImpl::mapCommentRow,
                userId,
                comment.getDishId()
        );
        if (check.size() > 0) deleteComment(comment.getId(), userId);
        return jdbcTemplate.query("INSERT INTO COMMENT (user_id, dish_id, text) VALUES (?, ?, ?) RETURNING id, user_id, dish_id, text, timestamp",
                DishDaoImpl::mapCommentRow,
                userId,
                comment.getDishId(),
                comment.getText()
        );
    }

    @Override
    public List<Comment> deleteComment(int id, int userId) {
        return jdbcTemplate.query("DELETE FROM COMMENT WHERE id = ? and user_id = ?  RETURNING id, user_id, dish_id, text, timestamp",
                DishDaoImpl::mapCommentRow,
                id,
                userId
        );
    }

    //Favourite
    @Override
    public boolean checkFavourite(int dishId, int userId) {
        int check = jdbcTemplate.query("SELECT id, user_id, dish_id FROM FAVOURITE WHERE user_id = ? and dish_id = ?",
                DishDaoImpl::mapFavouriteRow,
                userId,
                dishId
        ).size();
        return check > 0;
    }

    @Override
    public boolean addFavourite(int userId, int dishId) {
        int check = jdbcTemplate.query("INSERT INTO FAVOURITE (user_id, dish_id) VALUES (?, ?) RETURNING id, user_id, dish_id",
                DishDaoImpl::mapFavouriteRow,
                userId,
                dishId
        ).size();
        return check > 0;
    }

    @Override
    public List<Favourite> removeFavourite(int id, int userId) {
        return jdbcTemplate.query("DELETE FROM FAVOURITE WHERE id = ? and user_id = ? RETURNING id, user_id, dish_id",
                DishDaoImpl::mapFavouriteRow,
                id,
                userId
        );
    }

    @Override
    public List<DishFormat> getFavourite(int userId) {
        return jdbcTemplate.query("SELECT dish.id, dish.title, dish.description, dish.category, dish.receipt, dish.image_id, dish.is_active, dish.likes, CASE WHEN fav.id IS NULL THEN false ELSE true END AS is_favourite FROM DISH, (SELECT favourite.* FROM favourite WHERE favourite.user_id = ?) AS fav WHERE dish.id=fav.dish_id",
                DishDaoImpl::mapDishFormatRow,
                userId
        );
    }

    //Labels
    @Override
    public List<Label> readLabelRelation(int id) {
        return jdbcTemplate.query("SELECT label.id, label.title FROM label LEFT JOIN dish_label ON label.id=dish_label.label_id WHERE dish_label.dish_id = ?", DishDaoImpl::mapLabelRow, id);
    }

    @Override
    public List<Label> createLabel(Label label) {
        return jdbcTemplate.query("INSERT INTO label (title) VALUES (?) RETURNING id, title",
                DishDaoImpl::mapLabelRow,
                label.getTitle()
        );
    }

    @Override
    public List<Label> getLabels(int limit, int offset) {
        return jdbcTemplate.query("SELECT id, title FROM label LIMIT ? OFFSET ?",
                DishDaoImpl::mapLabelRow,
                limit,
                offset
        );
    }

    @Override
    public List<Label> editLabel(Label label) {
        return jdbcTemplate.query("UPDATE label SET title = ? WHERE id = ? RETURNING id, title",
                DishDaoImpl::mapLabelRow,
                label.getTitle(),
                label.getId()
        );
    }

    @Override
    public List<Label> deleteLabel(int id) {
        return jdbcTemplate.query("DELETE FROM label WHERE id = ? RETURNING id, title",
                DishDaoImpl::mapLabelRow,
                id
        );
    }

    @Override
    public List<DishLabel> addLabel(DishLabel label) {
        return jdbcTemplate.query("INSERT INTO dish_label (dish_id, label_id) VALUES (?, ?) RETURNING id, dish_id, label_id",
                DishDaoImpl::mapDishLabelRow,
                label.getDish(),
                label.getLabel()
        );
    }

    @Override
    public List<DishLabel> removeLabel(int id) {
        return jdbcTemplate.query("DELETE FROM dish_label WHERE id = ? RETURNING id, dish_id, label_id",
                DishDaoImpl::mapDishLabelRow,
                id
        );
    }

    //Like
    @Override
    public boolean setLike(int dishId) {
        jdbcTemplate.query("UPDATE DISH SET likes = likes+1 WHERE dish.id = ? RETURNING id, title, description, category, receipt, image_id, is_active, likes", DishDaoImpl::mapDishRow, dishId);
        return true;
    }
}
