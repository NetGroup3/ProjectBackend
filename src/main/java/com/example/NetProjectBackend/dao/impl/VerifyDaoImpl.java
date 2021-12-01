package com.example.NetProjectBackend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import com.example.NetProjectBackend.confuguration.query.VerifyQuery;
import com.example.NetProjectBackend.dao.VerifyDao;
import com.example.NetProjectBackend.models.Verify;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class VerifyDaoImpl implements VerifyDao {

    private final JdbcTemplate jdbcTemplate;
    private final VerifyQuery q;


    private static Verify mapVerifyRow(ResultSet rs, int rowNum) throws SQLException {
        return new Verify(
            rs.getInt("user_id"),
            rs.getString("verify_code"),
            rs.getObject("timestamp", OffsetDateTime.class)
        );
    }

    @Override
    public void create(Verify ver) {
        jdbcTemplate.update(q.getInsert(), ver.getUserId(), ver.getCode()); //, ver.getTimestamp()
    }

    @Override
    public Verify readById(int userId) {
        Verify ver = null;
        try {
            ver = jdbcTemplate.queryForObject(q.getSelectById(), VerifyDaoImpl::mapVerifyRow, userId);
        }
        catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Verify with user_id {}", userId);
        }
        return ver;
    }

    @Override
    public Verify readByCode(String code) {
        Verify ver = null;
        try {
            ver = jdbcTemplate.queryForObject(q.getSelectByCode(), VerifyDaoImpl::mapVerifyRow, code);
        }
        catch (DataAccessException dataAccessException) {
            log.debug("Couldn't find entity of type Verify with code {}", code);
        }
        return ver;
    }

    @Override
    public void update(Verify ver) {
        jdbcTemplate.update(q.getUpdate(), ver.getCode(), /*ver.getTimestamp() ,*/ ver.getUserId());
        
    }

    @Override
    public void delete(int userId) {
        jdbcTemplate.update(q.getDelete(), userId);
        
    }
    
}
