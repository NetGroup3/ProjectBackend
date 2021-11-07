package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaolmpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_FROM_CLIENT = "SELECT * FROM CLIENT";
    private static final String SELECT_ALL_FROM_CLIENT_WHERE_ID = "SELECT * FROM CLIENT WHERE ID = ?";
    private static final String INSERT_INTO_CLIENT_VALUES = "INSERT INTO CLIENT (nickname, password, firstname, lastname, email) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE CLIENT SET password = ?, nickname = ?, firstname = ?, lastName = ?, email = ?, picture = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM CLIENT WHERE ID = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private static User mapClientRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("nickname"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getTime("timestamp"),
                rs.getString("picture"),
                rs.getBoolean("status"),
                rs.getInt("role")
        );
    }

    public UserDaolmpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL_FROM_CLIENT, UserDaolmpl::mapClientRow);
    }

    @Override
    public User read(int id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_ALL_FROM_CLIENT_WHERE_ID, new Object[]{id}, UserDaolmpl::mapClientRow);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
        }
        return user;
    }

    //                     NEED CHECK
    @Override
    public int create(User user) {
        //assert jdbcTemplate.update(INSERT_INTO_CLIENT_VALUES, user.getNickname(), user.getPassword(),  user.getFirstname(), user.getLastname(), user.getEmail()) > 0;
        return jdbcTemplate.update(INSERT_INTO_CLIENT_VALUES, user.getNickname(), user.getPassword(),  user.getFirstname(), user.getLastname(), user.getEmail());
    }

    //                     NEED CHECK
    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_CLIENT, user.getPassword(), user.getNickname(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPicture(), user.getId());
    }

    //                     NEED CHECK
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CLIENT, id);
    }
}
