package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequestDto;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;

import java.util.List;

public interface FriendService {
    void addFriend(Friend friend);

    void acceptInvite(int id);

    void declineInvite(int id);

    void removeFriend(int id);

    List<FriendResponseDto> readFriends(int limit, int offset, int id);

    List<FriendResponseDto> readRequests(int limit, int offset, int id);

}
