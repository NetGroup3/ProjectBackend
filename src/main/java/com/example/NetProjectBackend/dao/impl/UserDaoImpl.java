package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.UserListRequest;
import com.example.NetProjectBackend.models.UserListRequest.SortProps;

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
    private static final String SELECT_BY_ID = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT WHERE ID = ?";
    private static final String SELECT_BY_EMAIL = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT WHERE email = ?";
    private static final String SELECT_BY_NAME = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT WHERE name = ?";
    private static final String INSERT_INTO_CLIENT_VALUES = "INSERT INTO CLIENT (password, first_name, last_name, email, timestamp, status, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_CLIENT = "UPDATE CLIENT SET password = ?, first_name = ?, last_name = ?, email = ?, image_id = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM CLIENT WHERE ID = ?";
    private static final String UPDATE_STATUS = "UPDATE CLIENT SET status = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE CLIENT SET password = ? WHERE id = ?";
    private static final String SELECT_PAGE = "SELECT id, password, first_name, last_name, email, timestamp, image_id, status, role FROM CLIENT ORDER BY id ASC LIMIT ? OFFSET ?";
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

    private String generateOrderByPart(UserListRequest req) {

        StringBuilder ORDER_BY = new StringBuilder();

        if (req.getSortProps() != null) {
            for (SortProps sp : req.getSortProps()) {
                String column = sp.getColumn();
                if (column != null && (column.equals("first_name") || column.equals("last_name") || column.equals("email") || column.equals("timestamp")) && !ORDER_BY.toString().contains(column)) {

                    if (ORDER_BY.toString().equals("")) {
                        ORDER_BY.append(" ORDER BY ");
                    }
                    else {
                        ORDER_BY.append(", ");
                    }

                    ORDER_BY.append(column);

                    if (sp.getAsc() != null && !sp.getAsc()) {
                        ORDER_BY.append(" DESC");
                    }
                    else {
                        ORDER_BY.append(" ASC");
                    }
                }
            }
        }

        return ORDER_BY.toString();
    }

    private String generateSelectSuitableQuery(UserListRequest req) {

        String SELECT_SUITABLE_USERS = "SELECT * FROM client WHERE LOWER(first_name) LIKE LOWER(?) AND LOWER(last_name) LIKE LOWER(?) AND LOWER(email) LIKE LOWER(?) AND LOWER(role) LIKE LOWER(?)";

        if (req.getFilterTimestamp() != null) {
            SELECT_SUITABLE_USERS += " AND timestamp ";
            if (req.getFilterTimestampAfter() != null) {
                if (!req.getFilterTimestampAfter()) {
                    SELECT_SUITABLE_USERS += "<= ?";
                }
                else {
                    SELECT_SUITABLE_USERS += ">= ?";
                }
            }
            else {
                SELECT_SUITABLE_USERS += ">= ?";
            }
            
        }

        SELECT_SUITABLE_USERS += generateOrderByPart(req);

        return SELECT_SUITABLE_USERS;

    }

    @Override
    public List<User> getAllSuitable(UserListRequest req) {

        String SELECT_SUITABLE_USERS = generateSelectSuitableQuery(req);

        List<User> users = null;

        try {
            if (req.getFilterTimestamp() == null) {
                users = jdbcTemplate.query(SELECT_SUITABLE_USERS, UserDaoImpl::mapClientRow,
                    "%" + req.getSearchFirstname() + "%",
                    "%" + req.getSearchLastname() + "%",
                    "%" + req.getSearchEmail() + "%",
                    "%" + req.getSearchRole() + "%"
                );
            }
            else {
                users = jdbcTemplate.query(SELECT_SUITABLE_USERS, UserDaoImpl::mapClientRow,
                    "%" + req.getSearchFirstname() + "%",
                    "%" + req.getSearchLastname() + "%",
                    "%" + req.getSearchEmail() + "%",
                    "%" + req.getSearchRole() + "%",
                    req.getFilterTimestamp()
                );
            }
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("WARNING\n\n" + dataAccessException);
        }

        return users;
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
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
        }
        return user;
    }

    @Override
    public User readByEmail(String email) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, UserDaoImpl::mapClientRow, email);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with email {}", email);
        }
        return user;
    }

    @Override
    public User readByName(String name) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_BY_NAME, new Object[]{name}, UserDaoImpl::mapClientRow);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with name {}", name);
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

    @Override
    public void changeStatus(EStatus status, int id) {
        jdbcTemplate.update(UPDATE_STATUS, EStatus.ACTIVE.getAuthority(), id);
    }

    @Override
    public void updatePassword(String password, int id) {
        jdbcTemplate.update(UPDATE_PASSWORD, password, id);
    }

    @Override
    public List<User> readPage(int limit, int offset) {
        List<User> users = null;
        try {
            users = jdbcTemplate.query(SELECT_PAGE, UserDaoImpl::mapUserRow, limit, offset);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Users with limit {} and offset {}", limit, offset);
        }
        return users;
    }

    private static User mapUserRow(ResultSet resultSet, int i) {
        return null;
    }

}
