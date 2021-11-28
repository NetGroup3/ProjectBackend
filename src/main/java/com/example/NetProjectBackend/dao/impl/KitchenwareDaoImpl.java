package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.IngredientConfig;
import com.example.NetProjectBackend.confuguration.query.KitchenwareConfig;
import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.models.Kitchenware;
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
public class KitchenwareDaoImpl implements KitchenwareDao {

    private final JdbcTemplate jdbcTemplate;
    private final KitchenwareConfig q;

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

    @Override
    public int create(Kitchenware kitchenware) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(q.getInsert(), Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, kitchenware.getTitle());
                        ps.setString(2, kitchenware.getDescription());
                        ps.setString(3, kitchenware.getCategory());
                        ps.setString(4, kitchenware.getImage_id());
                        ps.setBoolean(5, kitchenware.isActive());
                        return ps;
                    }
                }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public Kitchenware read(int id) {
        Kitchenware kitchenware = null;
        log.info("test");
        try {
            kitchenware = jdbcTemplate.queryForObject(q.getSelect(), KitchenwareDaoImpl::mapKitchenwareRow, id);
        }
        catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Kitchenware with id {}", id);
        }
        return kitchenware;
    }

    @Override
    public void update(Kitchenware kitchenware) {
        jdbcTemplate.update(
                q.getUpdate(),
                kitchenware.getTitle(),
                kitchenware.getDescription(),
                kitchenware.getCategory(),
                kitchenware.getImage_id(),
                kitchenware.isActive(),
                kitchenware.getId()
        );
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(q.getDelete(), id);
    }

    @Override
    public List<Kitchenware> readPage(int limit, int offset) {
        List<Kitchenware> kitchenware = null;
        try {
            kitchenware = jdbcTemplate.query(q.getSelectPage(), KitchenwareDaoImpl::mapKitchenwareRow, limit, offset);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Kitchenware with limit {} and offset {}", limit, offset);
        }
        return kitchenware;
    }

    @Override
    public List<Kitchenware> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        List<Kitchenware> kitchenware = null;
        try {
            if (sortedBy.equals("id")) {
                kitchenware = jdbcTemplate.query(q.getSelectSearchPageById(), KitchenwareDaoImpl::mapKitchenwareRow, key, category, limit, offset);
            } else if (sortedBy.equals("title")) {
                kitchenware = jdbcTemplate.query(q.getSelectSearchPageByTitle(), KitchenwareDaoImpl::mapKitchenwareRow, key, category, limit, offset);
            } else if (sortedBy.equals("category")) {
                kitchenware = jdbcTemplate.query(q.getSelectSearchPageByCategory(), KitchenwareDaoImpl::mapKitchenwareRow, key, category, limit, offset);
            }
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Kitchenware with limit {} and offset {}", limit, offset);
        }
        return kitchenware;
    }

}