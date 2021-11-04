package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Client;
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

    public ClientDaolmplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Client> getAll() {
        return jdbcTemplate.query(SELECT_ALL_FROM_CLIENT, ClientDaolmplementation::mapClientRow);
    }

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


    //                     NEED EDITS
    @Override
    public List<Integer> findClientById(int id) {

        return null;
    }

    @Override
    public void create(Client client) {

    }

    @Override
    public Client read(int id) {
        Client client = null;
        try {
            person = jdbcTemplate.queryForObject("select * from client where id = ?", new Object[]{id}, ROW_MAPPER);
        } catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Person with id {}", id);
        }
        return client;
    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Client client) {
        jdbcTemplate.update("delete from client where id = ?", id);
    }
}
