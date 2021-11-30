package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.UserStockConfig;
import com.example.NetProjectBackend.dao.UserStockDao;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.UserStockElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class UserStockDaoImpl implements UserStockDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserStockConfig userStockConfig;

    private static UserStockElement mapUserStockRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserStockElement(
                rs.getInt("id"),
                rs.getInt("user_id"),
                new Ingredient(rs.getInt("ingredient_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("image_id"),
                        rs.getBoolean("is_active"),
                        rs.getString("measurement")),
                rs.getInt("amount")
        );
    }

    private static Integer mapIngredientIdRow(ResultSet rs, int rowNum) throws SQLException {
        return new Integer(rs.getInt("id"));
    }

    @Override
    public List<UserStockElement> readStock(int userId) {
        List<UserStockElement> userStockElements = null;
        try {
            userStockElements = jdbcTemplate.query(userStockConfig.getSelect(), UserStockDaoImpl::mapUserStockRow, userId);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find user with id {}", userId);
        }
        return userStockElements;
    }

    @Override
    public void deleteStockElement(int userId, int ingredientId) {
        jdbcTemplate.update(userStockConfig.getDelete(), userId, ingredientId);
    }

    @Override
    public UserStockElement readStockElement(int userId, int ingredientId) {
        UserStockElement userStockElement = null;
        try {
            userStockElement = jdbcTemplate.queryForObject(userStockConfig.getSelectByUserIdAndIngredientId(), UserStockDaoImpl::mapUserStockRow, userId, ingredientId);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find user with id {}", userId);
        }
        return userStockElement;
    }

    @Override
    public UserStockElement updateStockElement(int userId, int ingredientId, int amount) {
        jdbcTemplate.update(userStockConfig.getUpdateByUserIdAndIngredientId(), amount, userId, ingredientId);
        return readStockElement(userId, ingredientId);
    }

    @Override
    public int ingredientExist(String ingredient) {
        Integer ingredientId = -1;
        try {
            ingredientId = jdbcTemplate.queryForObject(userStockConfig.getSelectIngredientId(), UserStockDaoImpl::mapIngredientIdRow, ingredient);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find ingredient with id {}", ingredient);
        }
        return ingredientId;
    }

    @Override
    public UserStockElement createStockElement(int userId, int ingredientId, int amount) {
        jdbcTemplate.update(userStockConfig.getInsert(), userId, ingredientId, amount);
        return readStockElement(userId, ingredientId);
    }
}
