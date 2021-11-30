package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendResponse;

import java.util.List;

public interface FriendDao {

    void create(Friend friend);

    void update(String status, int id);

    void delete(int id);

    List<FriendResponse> readFriends(int id, String status);

    List<FriendResponse> readRequests(int id, String status);

}
