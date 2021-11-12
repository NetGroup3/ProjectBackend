package com.example.NetProjectBackend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.example.NetProjectBackend.models.Verify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VerifyDaoImpl implements VerifyDao{

    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyDao.class);

    private static final String SELECT_BY_ID = "SELECT user_id, verify_code, timestamp FROM VERIFY WHERE user_id = ?";
    private static final String INSERT = "INSERT INTO VERIFY (user_id, verify_code, timestamp) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE VERIFY SET verify_code = ?, timestamp = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM VERIFY WHERE user_id = ?";


    private static Verify mapVerifyRow(ResultSet rs, int rowNum) throws SQLException {
        return new Verify(
            rs.getInt("user_id"),
            rs.getString("verify_code"),
            rs.getObject("timestamp", OffsetDateTime.class)
        );
    }

    public VerifyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Verify ver) {
        jdbcTemplate.update(INSERT, ver.getUserId(), ver.getCode(), ver.getTimestamp());
    }

    @Override
    public Verify read(int userId) {
        Verify ver = null;
        try {
            ver = jdbcTemplate.queryForObject(SELECT_BY_ID, VerifyDaoImpl::mapVerifyRow, userId);
        }
        catch (DataAccessException dataAccessException) {
            LOGGER.debug("Couldn't find entity of type Verify with user_id {}", userId);
        }
        return ver;
    }

    @Override
    public void update(Verify ver) {
        jdbcTemplate.update(UPDATE, ver.getCode(), ver.getTimestamp(), ver.getUserId());
        
    }

    @Override
    public void delete(int userId) {
        jdbcTemplate.update(DELETE, userId);
        
    }
    
}
