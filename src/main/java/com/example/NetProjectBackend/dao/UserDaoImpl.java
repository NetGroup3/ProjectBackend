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
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_FROM_CLIENT = "SELECT id, password, firstname, lastname, email, timestamp, picture, status, role FROM CLIENT";
    //select id, password, firstname, lastname, email, timestamp, picture, status, role from client where id=26;
    private static final String SELECT_ALL_FROM_CLIENT_WHERE_ID = "SELECT id, password, firstname, lastname, email, timestamp, picture, status, role FROM CLIENT WHERE ID = ?";

    //INSERT INTO public.client (id, password, firstname, lastname, email, timestamp, picture, status, role)
    //              VALUES (DEFAULT, 'pasword_1234', 'John_firstname', 'miller_last_name', '1@1.com', '2020-08-10 10:41:22.276000 +00:00', 'picture_url', false, 10)
    private static final String INSERT_INTO_CLIENT_VALUES = "INSERT INTO CLIENT (password, firstname, lastname, email, timestamp, status, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_CLIENT = "UPDATE CLIENT SET password = ?, firstname = ?, lastName = ?, email = ?, picture = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM CLIENT WHERE ID = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private static User mapClientRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getString("picture"),
                rs.getString("status"),
                rs.getInt("role")
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
    public User read(int id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_ALL_FROM_CLIENT_WHERE_ID, new Object[]{id}, UserDaoImpl::mapClientRow);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
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
                ps.setInt(7, user.getRole());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    //                     NEED CHECK
    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_CLIENT, user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPicture(), user.getId());
    }

    //                     NEED CHECK
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CLIENT, id);
    }
}
