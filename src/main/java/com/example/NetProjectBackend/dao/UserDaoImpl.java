package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_FROM_CLIENT = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT";
    //select id, password, firstname, lastname, email, timestamp, picture, status, role from client where id=26;
    private static final String SELECT_BY_ID = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT WHERE ID = ?";
    private static final String SELECT_BY_EMAIL = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT WHERE email = ?";

    //INSERT INTO public.client (id, password, firstname, lastname, email, timestamp, picture, status, role)
    //              VALUES (DEFAULT, 'pasword_1234', 'John_firstname', 'miller_last_name', '1@1.com', '2020-08-10 10:41:22.276000 +00:00', 'picture_url', false, 10)
    private static final String INSERT_INTO_CLIENT_VALUES = "INSERT INTO CLIENT (password, first_name, last_name, email, timestamp, status, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_CLIENT = "UPDATE CLIENT SET password = ?, first_name = ?, last_name = ?, email = ?, image_id = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM CLIENT WHERE ID = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private static User mapClientRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getString("image_id"),
                rs.getString("status"),
                rs.getString("role")
        );
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL_FROM_CLIENT, UserDaoImpl::mapClientRow);
    }

    @Override
    public User readById(int id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_BY_ID, UserDaoImpl::mapClientRow, id);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
        }
        return user;
    }

    @Override
    public User readByEmail(String email) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, UserDaoImpl::mapClientRow, email);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with email {}", email);
        }
        return user;
    }

    //                     NEED CHECK
    @Override
    public int create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
        new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_INTO_CLIENT_VALUES, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getPassword());
                ps.setString(2, user.getFirstname());
                ps.setString(3, user.getLastname());
                ps.setString(4, user.getEmail());
                ps.setObject(5, user.getTimestamp());
                ps.setString(6, user.getStatus());
                ps.setString(7, user.getRole());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    //                     NEED CHECK
    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_CLIENT, user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getImageId(), user.getId());
    }

    //                     NEED CHECK
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CLIENT, id);
    }
}
