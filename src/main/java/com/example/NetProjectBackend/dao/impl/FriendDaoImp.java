package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.FriendQuery;
import com.example.NetProjectBackend.dao.FriendDao;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class FriendDaoImp implements FriendDao {

    private final JdbcTemplate jdbcTemplate;
    private final FriendQuery q;

    private static FriendResponseDto mapFriendRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponseDto(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("image_id")
        );
    }

    private static FriendResponseDto mapRequestRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponseDto(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("image_id")
        );
    }

    /**
     * addFriend
     */
    @Override
    public void create(int recipientId, int senderId, String status, OffsetDateTime timestamp) {
        jdbcTemplate.update(
                q.getInsert(),
                senderId,
                recipientId,
                status,
                timestamp
        );
    }

    /**
     * acceptInvite
     */
    @Override
    public void update(String status, int id) {
        jdbcTemplate.update(
                q.getUpdate(),
                status,
                id
        );
    }

    /**
     * removeFriend, declineInvite
     */
    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                q.getDelete(),
                id
        );
    }

    @Override
    public List<FriendResponseDto> readFriends(String status, int limit, int offset, int id) {
        List<FriendResponseDto> friends = new ArrayList<>();
        try {
            friends =
                    jdbcTemplate.query(
                            q.getSelectFriendId(),
                            FriendDaoImp::mapFriendRow,
                            id,
                            id,
                            status,
                            limit,
                            offset
                    );
            log.info(String.valueOf(friends));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return friends;
    }

    @Override
    public List<FriendResponseDto> readRequests(String status, int limit, int offset, int id) {
        List<FriendResponseDto> requests = new ArrayList<>();
        try {
            requests =
                    jdbcTemplate.query(
                            q.getSelectRequest(),
                            FriendDaoImp::mapRequestRow,
                            status,
                            id,
                            limit,
                            offset
                    );
            log.info(String.valueOf(requests));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return requests;
    }

}
