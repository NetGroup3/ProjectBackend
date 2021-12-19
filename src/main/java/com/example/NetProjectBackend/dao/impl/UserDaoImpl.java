package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.UserQuery;
import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private static UserPaginated mapUserPaginated(ResultSet rs, int rowNum) throws SQLException {
        return new UserPaginated(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getString("image_id"),
                rs.getString("status"),
                rs.getString("role"),
                rs.getInt("total")
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
                rs.getObject("timestamp", OffsetDateTime.class),
                rs.getBoolean("check_user")
        );
    }


    private String generateOrderByPart(UserListRequest req) {

        StringBuilder ORDER_BY = new StringBuilder();

        if (req.getSortProps() != null) {
            for (SortProps sp : req.getSortProps()) {
                String column = sp.getColumn();
                if (column != null && (
                   column.equals("first_name") ||
                   column.equals("last_name") ||
                   column.equals("email") ||
                   column.equals("timestamp")
                ) && !ORDER_BY.toString().contains(column)) {

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

        String SELECT_SUITABLE_USERS = q.getSelectAllSuitablePart();
        SELECT_SUITABLE_USERS += generateOrderByPart(req);
        SELECT_SUITABLE_USERS += " LIMIT ? OFFSET ?";
        return SELECT_SUITABLE_USERS;
    }

    @Override
    public List<UserPaginated> getAllSuitable(UserListRequest req) {

        String SELECT_SUITABLE_USERS = generateSelectSuitableQuery(req);

        List<Object> args = new ArrayList<>();

        args.add(req.getSearchFirstname());
        args.add(req.getSearchLastname());
        args.add(req.getSearchEmail());
        args.add(req.getSearchRole());
        args.add(req.getPerPage());
        args.add((req.getPageNo() - 1) * req.getPerPage());

        return jdbcTemplate.query(SELECT_SUITABLE_USERS, UserDaoImpl::mapUserPaginated, args.toArray());
    }

    @Override
    public User readById(int id) {
        return jdbcTemplate.queryForObject(q.getSelectById(), UserDaoImpl::mapClientRow, id);
    }

    @Override
    public User readByEmail(String email) {
        return jdbcTemplate.queryForObject(q.getSelectByEmail(), UserDaoImpl::mapClientRow, email);
    }

    @Override
    public User readByName(String name) {
        return jdbcTemplate.queryForObject(q.getSelectByName(), new Object[]{name}, UserDaoImpl::mapClientRow);
    }

    @Override
    public List<UserSearchDto> readUsers(String name) {
        return jdbcTemplate.query(
                q.getSelectUserByName(),
                UserDaoImpl::mapUserSearchRow,
                "%" + name + "%",
                "%" + name + "%"
        );
    }

    @Override
    public UserProfileDto readUser(int id, boolean checkUser) {
        return jdbcTemplate.queryForObject(
                q.getSelectUserById(),
                UserDaoImpl::mapUserProfileRow,
                checkUser,
                id
        );
    }

    @Override
    public int create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(q.getInsertIntoClientValues(), Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getPassword());
                    ps.setString(2, user.getFirstname());
                    ps.setString(3, user.getLastname());
                    ps.setString(4, user.getEmail());
                    ps.setObject(5, user.getTimestamp());
                    ps.setString(6, user.getStatus());
                    ps.setString(7, user.getRole());
                    return ps;
                }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(q.getUpdateClient(), user.getFirstname(), user.getLastname(), user.getId());
    }

    @Override
    public void updateFull(User user) {
        jdbcTemplate.update(q.getUpdateFullClient(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getId());
    }

    @Override
    public void updateImageId(int userId, String imageId) {
        jdbcTemplate.update(q.getUpdateClientImage(), imageId, userId);
    }

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
}
