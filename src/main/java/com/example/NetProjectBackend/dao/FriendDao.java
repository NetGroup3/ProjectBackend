package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequestDto;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;

import java.util.List;

public interface FriendDao {

    void create(Friend friend);

    void update(String status, int id);

    void delete(int id);

    List<FriendResponseDto> readFriends(FriendRequestDto friendRequestDto, int id);

    List<FriendResponseDto> readRequests(FriendRequestDto friendRequestDto, int id);

}
