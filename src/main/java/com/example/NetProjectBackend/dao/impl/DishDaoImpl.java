package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.DishQuery;
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
    private final DishQuery q;

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

    private static DishIngredientDto mapRelationIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new DishIngredientDto(
                rs.getInt("id"),
                rs.getInt("dish_id"),
                rs.getInt("ingredient_id"),
                rs.getBigDecimal("ingredient_amount")
        );
    }

    private static DishKitchenwareDto mapRelationKitchenware(ResultSet rs, int rowNum) throws SQLException {
        return new DishKitchenwareDto(
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

    private static CommentDto mapCommentViewRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommentDto(
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

    private static DishLabelDto mapDishLabelRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishLabelDto(
                rs.getInt("id"),
                rs.getInt("dish_id"),
                rs.getInt("label_id")
        );
    }

    private static DishFormatDto mapDishFormatRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishFormatDto(
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

    private static DishRecommendDto mapDishRecommendRow(ResultSet rs, int rowNum) throws SQLException {
        return new DishRecommendDto(
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
        return jdbcTemplate.query(q.getCreate(),
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
        return jdbcTemplate.query(q.getEdit(),
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
        return jdbcTemplate.query(q.getDelete(), DishDaoImpl::mapDishRow, id);
    }

    @Override
    public List<DishFormatDto> readList(DishSearchDto search, Integer userId) {
        if (search.getTitle() == null && search.getCategory() == null) {
            if (search.isDesc()) return jdbcTemplate.query(
                    q.getReadPageDesc(),
                    DishDaoImpl::mapDishFormatRow,
                    userId, search.getLimit(),
                    search.getTitle()
            );
            return jdbcTemplate.query(
                    q.getReadPageAsc(),
                    DishDaoImpl::mapDishFormatRow,
                    userId,
                    search.getLimit(),
                    search.getTitle()
            );
        }
        if (search.isDesc()) return jdbcTemplate.query(
                q.getReadParamsDesc(),
                DishDaoImpl::mapDishFormatRow,
                userId, search.getTitle(),
                search.getCategory(),
                search.getLimit(),
                search.getOffset());
        return jdbcTemplate.query(
                q.getReadParamsAsc(),
                DishDaoImpl::mapDishFormatRow,
                userId, search.getTitle(),
                search.getCategory(),
                search.getLimit(),
                search.getOffset()
        );
    }

    @Override
    public int setActive(int id, boolean active) {
        return jdbcTemplate.update(q.getSetActive(), active, id);
    }

    @Override
    public DishFormatDto soloReadDish(int id, int userId) {
        return jdbcTemplate.query(q.getSoloRead(), DishDaoImpl::mapDishFormatRow, userId, id).get(0);
    }

    @Override
    public List<DishRecommendDto> getRecommend(int userId, int limit, int offset) {
        return jdbcTemplate.query(
                q.getRecommend(),
                DishDaoImpl::mapDishRecommendRow,
                userId,
                limit,
                offset,
                userId
        );
    }

    @Override
    public List<Dish> getWithIngredients(String list, int limit, int offset) {
        return jdbcTemplate.query(
                q.getWithIngredientsLeft()+list+q.getWithIngredientsRight(),
                DishDaoImpl::mapDishRow,
                limit,
                offset);
    }


    //Dish Ingredient
    @Override
    public List<DishIngredientDto> checkIngredient(int dishId, int ingredientId) {
        return jdbcTemplate.query(q.getIngredientCheck(), DishDaoImpl::mapRelationIngredient, dishId, ingredientId);
    }

    @Override
    public List<DishIngredientDto> updateIngredient(int id, BigDecimal amount) {
        return jdbcTemplate.query(q.getIngredientUpdate(), DishDaoImpl::mapRelationIngredient, amount, id);
    }

    @Override
    public List<DishIngredientDto> removeIngredient(int id) {
        return jdbcTemplate.query(q.getIngredientRemove(), DishDaoImpl::mapRelationIngredient, id);
    }

    @Override
    public List<DishIngredientDto> createDishIngredient(DishIngredientDto dishIngredientDto) {
        return jdbcTemplate.query(
                q.getIngredientCreate(),
                DishDaoImpl::mapRelationIngredient,
                dishIngredientDto.getDish(),
                dishIngredientDto.getIngredient(),
                dishIngredientDto.getAmount()
        );

    }

    @Override
    public List<Ingredient> readIngredientsRelation(int id) {
        return jdbcTemplate.query(q.getIngredientRead(), DishDaoImpl::mapIngredientRow, id);
    }

    //Dish Kitchenware
    @Override
    public List<DishKitchenwareDto> checkKitchenware(DishKitchenwareDto dishKitchenwareDto) {
        return jdbcTemplate.query(
                q.getKitchenwareCheck(),
                DishDaoImpl::mapRelationKitchenware,
                dishKitchenwareDto.getDish(),
                dishKitchenwareDto.getKitchenware());
    }

    @Override
    public List<DishKitchenwareDto> removeKitchenware(int id) {
        return jdbcTemplate.query(q.getKitchenwareRemove(), DishDaoImpl::mapRelationKitchenware, id);
    }

    @Override
    public List<DishKitchenwareDto> createDishKitchenware(DishKitchenwareDto dishKitchenwareDto) {
        return jdbcTemplate.query(q.getKitchenwareCreate(),
                DishDaoImpl::mapRelationKitchenware,
                dishKitchenwareDto.getDish(),
                dishKitchenwareDto.getKitchenware()
        );
    }

    @Override
    public List<Kitchenware> readKitchenwareRelation(int id) {
        return jdbcTemplate.query(q.getKitchenwareRead(), DishDaoImpl::mapKitchenwareRow, id);
    }

    //Comment
    @Override
    public List<CommentDto> readCommentRelation(int id) {
        return jdbcTemplate.query(q.getCommentRead(), DishDaoImpl::mapCommentViewRow, id);
    }

    @Override
    public List<Comment> createComment(Comment comment, int userId) {
        List<Comment> check = jdbcTemplate.query(q.getCommentCreateCheck(),
                DishDaoImpl::mapCommentRow,
                userId,
                comment.getDishId()
        );
        if (check.size() > 0) deleteComment(comment.getId(), userId);
        return jdbcTemplate.query(q.getCommentCreate(),
                DishDaoImpl::mapCommentRow,
                userId,
                comment.getDishId(),
                comment.getText()
        );
    }

    @Override
    public List<Comment> deleteComment(int id, int userId) {
        return jdbcTemplate.query(q.getCommentDelete(),
                DishDaoImpl::mapCommentRow,
                id,
                userId
        );
    }

    //Favourite
    @Override
    public boolean checkFavourite(int dishId, int userId) {
        int check = jdbcTemplate.query(q.getFavouriteCheck(),
                DishDaoImpl::mapFavouriteRow,
                userId,
                dishId
        ).size();
        return check > 0;
    }

    @Override
    public boolean addFavourite(int userId, int dishId) {
        int check = jdbcTemplate.query(q.getFavouriteAdd(),
                DishDaoImpl::mapFavouriteRow,
                userId,
                dishId
        ).size();
        return check > 0;
    }

    @Override
    public List<Favourite> removeFavourite(int id, int userId) {
        return jdbcTemplate.query(q.getFavouriteRemove(),
                DishDaoImpl::mapFavouriteRow,
                id,
                userId
        );
    }

    @Override
    public List<DishFormatDto> getFavourite(int userId) {
        return jdbcTemplate.query(q.getFavouriteGet(),
                DishDaoImpl::mapDishFormatRow,
                userId
        );
    }

    //Labels
    @Override
    public List<Label> readLabelRelation(int id) {
        return jdbcTemplate.query(q.getLabelReadRelation(), DishDaoImpl::mapLabelRow, id);
    }

    @Override
    public List<Label> createLabel(Label label) {
        return jdbcTemplate.query(q.getLabelCreate(),
                DishDaoImpl::mapLabelRow,
                label.getTitle()
        );
    }

    @Override
    public List<Label> getLabels(int limit, int offset) {
        return jdbcTemplate.query(q.getLabelGet(),
                DishDaoImpl::mapLabelRow,
                limit,
                offset
        );
    }

    @Override
    public List<Label> editLabel(Label label) {
        return jdbcTemplate.query(q.getLabelEdit(),
                DishDaoImpl::mapLabelRow,
                label.getTitle(),
                label.getId()
        );
    }

    @Override
    public List<Label> deleteLabel(int id) {
        return jdbcTemplate.query(q.getLabelDelete(),
                DishDaoImpl::mapLabelRow,
                id
        );
    }

    @Override
    public List<DishLabelDto> addLabel(DishLabelDto label) {
        return jdbcTemplate.query(q.getLabelAdd(),
                DishDaoImpl::mapDishLabelRow,
                label.getDish(),
                label.getLabel()
        );
    }

    @Override
    public List<DishLabelDto> removeLabel(int id) {
        return jdbcTemplate.query(q.getLabelRemove(),
                DishDaoImpl::mapDishLabelRow,
                id
        );
    }

    //Like
    @Override
    public boolean setLike(int dishId) {
        jdbcTemplate.query(q.getLikeSetLike(), DishDaoImpl::mapDishRow, dishId);
        return true;
    }
}
