package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.dto.FriendResponseDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface FriendDao {

    void create(int recipientId, int senderId, String status, OffsetDateTime timestamp);

    void update(String status, int id);

    void delete(int id);

    List<FriendResponseDto> readFriends(String status, int limit, int offset, int id);

    List<FriendResponseDto> readRequests(String status, int limit, int offset, int id);

}
