package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.FriendConfig;
import com.example.NetProjectBackend.dao.FriendDao;
import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequest;
import com.example.NetProjectBackend.models.dto.FriendResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class FriendDaoImp implements FriendDao {

    private final JdbcTemplate jdbcTemplate;
    private final FriendConfig q;

    private static FriendResponse mapFriendRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponse(
                rs.getInt("friend_id")
        );
    }

    private static FriendResponse mapRequestRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponse(
                rs.getInt("sender_id")
        );
    }

    /**
     * addFriend
     */
    @Override
    public void create(Friend friend) {
        jdbcTemplate.update(
                q.getInsert(),
                friend.getSenderId(),
                friend.getRecipientId(),
                friend.getStatus(),
                friend.getTimestamp()
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
     *  removeFriend, declineInvite
     */
    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                q.getDelete(),
                id
        );
    }

    @Override
    public List<FriendResponse> readFriends(FriendRequest friendRequest, int id) {
        List<FriendResponse> friends = new ArrayList<>();
        try {
            friends =
                    jdbcTemplate.query(
                            q.getSelectFriendId(),
                            FriendDaoImp::mapFriendRow,
                            id,
                            id,
                            friendRequest.getStatus(),
                            friendRequest.getLimit(),
                            friendRequest.getOffset()
                    );
            log.info(String.valueOf(friends));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return friends;
    }

    @Override
    public List<FriendResponse> readRequests(FriendRequest friendRequest, int id) {
        List<FriendResponse> requests = new ArrayList<>();
        try {
            requests =
                    jdbcTemplate.query(
                            q.getSelectRequest(),
                            FriendDaoImp::mapRequestRow,
                            friendRequest.getStatus(),
                            id,
                            friendRequest.getLimit(),
                            friendRequest.getOffset()
                    );
            log.info(String.valueOf(requests));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return requests;
    }

}
