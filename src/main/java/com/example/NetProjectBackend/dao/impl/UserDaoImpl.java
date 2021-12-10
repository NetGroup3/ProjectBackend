package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.UserQuery;
import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.UserListRequest;
import com.example.NetProjectBackend.models.UserListRequest.SortProps;
import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.dto.UserProfileDto;
import com.example.NetProjectBackend.models.dto.UserSearchDto;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserQuery q;

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

    private static UserSearchDto mapUserSearchRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserSearchDto(
                rs.getInt("id"),
                rs.getString("full_name")
        );
    }

    private static UserProfileDto mapUserProfileRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserProfileDto(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("image_id"),
                rs.getObject("timestamp", OffsetDateTime.class)
        );
    }


    private String generateOrderByPart(UserListRequest req) {

        StringBuilder ORDER_BY = new StringBuilder();

        if (req.getSortProps() != null) {
            for (SortProps sp : req.getSortProps()) {
                String column = sp.getColumn();
                if (column != null && (column.equals("first_name") || column.equals("last_name") || column.equals("email") || column.equals("timestamp")) && !ORDER_BY.toString().contains(column)) {

                    if (ORDER_BY.toString().equals("")) {
                        ORDER_BY.append(" ORDER BY ");
                    } else {
                        ORDER_BY.append(", ");
                    }

                    ORDER_BY.append(column);

                    if (sp.getAsc() != null && !sp.getAsc()) {
                        ORDER_BY.append(" DESC");
                    } else {
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
                } else {
                    SELECT_SUITABLE_USERS += ">= ?";
                }
            } else {
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
            } else {
                users = jdbcTemplate.query(SELECT_SUITABLE_USERS, UserDaoImpl::mapClientRow,
                        "%" + req.getSearchFirstname() + "%",
                        "%" + req.getSearchLastname() + "%",
                        "%" + req.getSearchEmail() + "%",
                        "%" + req.getSearchRole() + "%",
                        req.getFilterTimestamp()
                );
            }
        } catch (DataAccessException dataAccessException) {
            log.debug("WARNING\n\n" + dataAccessException);
        }

        return users;
    }


    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(q.getSelectAllFromClient(), UserDaoImpl::mapClientRow);
    }

    @Override
    public User readById(int id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(q.getSelectById(), UserDaoImpl::mapClientRow, id);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Person with id {}", id);
        }
        return user;
    }

    @Override
    public User readByEmail(String email) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(q.getSelectByEmail(), UserDaoImpl::mapClientRow, email);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Person with email {}", email);
        }
        return user;
    }

    @Override
    public User readByName(String name) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(q.getSelectByName(), new Object[]{name}, UserDaoImpl::mapClientRow);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Person with name {}", name);
        }
        return user;
    }

    @Override
    public List<UserSearchDto> readUsers(String name) {
        List<UserSearchDto> users = new ArrayList<>();
        try {
            users =
                    jdbcTemplate.query(
                            q.getSelectUserByName(),
                            UserDaoImpl::mapUserSearchRow,
                            "%" + name + "%",
                            "%" + name + "%"
                    );
            log.info(String.valueOf(users));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return users;
    }

    @Override
    public UserProfileDto readUser(int id) {
        UserProfileDto user = new UserProfileDto();
        try {
            user =
                    jdbcTemplate.queryForObject(
                            q.getSelectUserById(),
                            UserDaoImpl::mapUserProfileRow,
                            id
                    );
            log.info(String.valueOf(user));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return user;
    }

    @Override
    public int create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(q.getInsertIntoClientValues(), Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.update(q.getUpdateClient(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getImageId(), user.getId());
    }

    //                     NEED CHECK
    @Override
    public void delete(int id) {
        jdbcTemplate.update(q.getDeleteClient(), id);
    }

    @Override
    public void changeStatus(EStatus status, int id) {
        jdbcTemplate.update(q.getUpdateStatus(), EStatus.ACTIVE.getAuthority(), id);
    }

    @Override
    public void updatePassword(String password, int id) {
        jdbcTemplate.update(q.getUpdatePassword(), password, id);
    }

    @Override
    public List<UserDto> readPage(int limit, int offset, String role) {
        List<UserDto> users = null;
        try {
            users = jdbcTemplate.query(q.getSelectPage(), UserDaoImpl::mapUserRow, role, limit, offset);
        } catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Users with status {}, limit {} and offset {}", role, limit, offset);
        }

        return users;
    }

    private static UserDto mapUserRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDto(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getString("image_id"),
                rs.getString("status"),
                rs.getString("role")
        );
    }

}
