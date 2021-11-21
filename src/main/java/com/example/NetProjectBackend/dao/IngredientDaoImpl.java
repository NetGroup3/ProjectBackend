package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Ingredient;
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
public class IngredientDaoImpl implements IngredientDao {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDao.class);

    private static final String INSERT = "INSERT INTO INGREDIENT (title, description, category, image_id, is_active, measurement) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String SELECT = "SELECT id, title, description, category, image_id, is_active, measurement FROM INGREDIENT WHERE id = ?";
    private static final String UPDATE = "UPDATE INGREDIENT SET title = ?, description = ?, category = ?, image_id = ?, is_active = ?, measurement = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM INGREDIENT WHERE id = ?";
    private static final String SELECT_PAGE = "SELECT id, title, description, category, image_id, is_active, measurement FROM INGREDIENT ORDER BY id ASC LIMIT ? OFFSET ?";

    public IngredientDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    @Override
    public int create(Ingredient ingredient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
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
        //jdbcTemplate.update(
        //        INSERT,
        //        ingredient.getTitle(),
        //        ingredient.getDescription(),
        //        ingredient.getCategory(),
        //        ingredient.getImage_id(),
        //        ingredient.is_active(),
        //        ingredient.getMeasurement()
        //);
    }

    @Override
    public Ingredient read(int id) {
        Ingredient ingredient = null;
        System.out.println("test");
        try {
            ingredient = jdbcTemplate.queryForObject(SELECT, IngredientDaoImpl::mapIngredientRow, id);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Ingredient with id {}", id);
        }
        return ingredient;
    }

    @Override
    public void update(Ingredient ingredient) {
        jdbcTemplate.update(
                UPDATE,
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
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public List<Ingredient> readPage(int limit, int offset) {
        List<Ingredient> ingredient = null;
        try {
            ingredient = jdbcTemplate.query(SELECT_PAGE, IngredientDaoImpl::mapIngredientRow, limit, offset);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Ingredient with limit {} and offset {}", limit, offset);
        }
        return ingredient;
    }

}
