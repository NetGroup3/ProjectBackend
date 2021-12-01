package com.example.NetProjectBackend.dao.impl;

import com.example.NetProjectBackend.confuguration.query.FriendQuery;
import com.example.NetProjectBackend.dao.FriendDao;
import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequestDto;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;
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
    private final FriendQuery q;

    private static FriendResponseDto mapFriendRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponseDto(
                rs.getInt("friend_id")
        );
    }

    private static FriendResponseDto mapRequestRow(ResultSet rs, int rowNum) throws SQLException {
        return new FriendResponseDto(
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
    public List<FriendResponseDto> readFriends(FriendRequestDto friendRequestDto, int id) {
        List<FriendResponseDto> friends = new ArrayList<>();
        try {
            friends =
                    jdbcTemplate.query(
                            q.getSelectFriendId(),
                            FriendDaoImp::mapFriendRow,
                            id,
                            id,
                            friendRequestDto.getStatus(),
                            friendRequestDto.getLimit(),
                            friendRequestDto.getOffset()
                    );
            log.info(String.valueOf(friends));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return friends;
    }

    @Override
    public List<FriendResponseDto> readRequests(FriendRequestDto friendRequestDto, int id) {
        List<FriendResponseDto> requests = new ArrayList<>();
        try {
            requests =
                    jdbcTemplate.query(
                            q.getSelectRequest(),
                            FriendDaoImp::mapRequestRow,
                            friendRequestDto.getStatus(),
                            id,
                            friendRequestDto.getLimit(),
                            friendRequestDto.getOffset()
                    );
            log.info(String.valueOf(requests));
        } catch (DataAccessException dataAccessException) {
            log.error(String.valueOf(dataAccessException));
        }
        return requests;
    }

}
