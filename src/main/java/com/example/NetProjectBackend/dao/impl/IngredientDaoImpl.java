package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.IngredientConfig;
import com.example.NetProjectBackend.dao.IngredientDao;
import com.example.NetProjectBackend.models.Ingredient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
@Slf4j
public class IngredientDaoImpl implements IngredientDao {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientConfig q;

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

    @Override
    public int create(Ingredient ingredient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(q.getInsert(), Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, ingredient.getTitle());
                        ps.setString(2, ingredient.getDescription());
                        ps.setString(3, ingredient.getCategory());
                        ps.setString(4, ingredient.getImage_id());
                        ps.setBoolean(5, ingredient.is_active());
                        ps.setString(6, ingredient.getMeasurement());
                        return ps;
                    }
                }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Ingredient read(int id) {
        Ingredient ingredient = null;
        log.info("test");
        try {
            log.info(q.getSelect());
            ingredient = jdbcTemplate.queryForObject(q.getSelect(), IngredientDaoImpl::mapIngredientRow, id);
        }
        catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Ingredient with id {}", id);
        }
        return ingredient;
    }

    @Override
    public void update(Ingredient ingredient) {
        jdbcTemplate.update(
                q.getUpdate(),
                ingredient.getTitle(),
                ingredient.getDescription(),
                ingredient.getCategory(),
                ingredient.getImage_id(),
                ingredient.is_active(),
                ingredient.getMeasurement(),
                ingredient.getId()
        );
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(q.getDelete(), id);
    }

    @Override
    public List<Ingredient> readPage(int limit, int offset) {
        List<Ingredient> ingredient = null;
        try {
            ingredient = jdbcTemplate.query(q.getSelectPage(), IngredientDaoImpl::mapIngredientRow, limit, offset);
        }
        catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Ingredient with limit {} and offset {}", limit, offset);
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        List<Ingredient> ingredient = null;
        try {
            if (sortedBy.equals("id")) {
                log.info(key);
                log.info(category);
                log.info(sortedBy);
                ingredient = jdbcTemplate.query(q.getSelectSearchPageById(), IngredientDaoImpl::mapIngredientRow, key, category, limit, offset);
            } else if (sortedBy.equals("title")) {
                ingredient = jdbcTemplate.query(q.getSelectSearchPageByTitle(), IngredientDaoImpl::mapIngredientRow, key, category, limit, offset);
            } else if (sortedBy.equals("category")) {
                ingredient = jdbcTemplate.query(q.getSelectSearchPageByCategory(), IngredientDaoImpl::mapIngredientRow, key, category, limit, offset);
            }
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Ingredients with limit {} and offset {}", limit, offset);
        }
        return ingredient;
    }

}
