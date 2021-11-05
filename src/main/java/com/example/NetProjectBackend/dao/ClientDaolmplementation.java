package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class ClientDaolmplementation implements ClientDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_FROM_CLIENT = "SELECT * FROM CLIENT";
    private static final String SELECT_ALL_FROM_CLIENT_WHERE_ID = "SELECT * FROM CLIENT WHERE ID = ?";
    private static final String INSERT_INTO_CLIENT_VALUES = "INSERT INTO CLIENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "";
    private static final String DELETE_CLIENT = "DELETE FROM CLIENT WHERE ID = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDao.class);

    private static Client mapClientRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(rs.getInt("id"),
                rs.getString("password"),
                rs.getString("nickname"),
                rs.getString("firstname"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getTime("timestamp"),
                rs.getString("picture"),
                rs.getBoolean("status"));
    }

    public ClientDaolmplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> getAll() {
        return jdbcTemplate.query(SELECT_ALL_FROM_CLIENT, ClientDaolmplementation::mapClientRow);
    }

    @Override
    public Client read(int id) {
        Client client = null;
        try {
            client = jdbcTemplate.queryForObject(SELECT_ALL_FROM_CLIENT_WHERE_ID, new Object[]{id}, ClientDaolmplementation::mapClientRow);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
        }
        return client;
    }

    //                     NEED CHECK
    @Override
    public void create(Client client) {
        assert jdbcTemplate.update(INSERT_INTO_CLIENT_VALUES, client.getId(), client.getPassword(), client.getNickname(), client.getFirstName(), client.getLastName(), client.getEmail()) > 0;
    }

    //                     NEED EDITS
    @Override
    public void update(Client client) {
        assert jdbcTemplate.update("update client set name = ?2, email = ?3 where id = ?1", client.getId(), client.getPassword(), client.getNickname(), client.getFirstName(), client.getLastName(), client.getEmail()) > 0;
    }

    //                     NEED CHECK
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CLIENT, id);
    }
}
