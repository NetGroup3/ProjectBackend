package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.dto.FriendResponseDto;

import java.util.List;

public interface FriendService {
    void addFriend(int recipientId);

    void acceptInvite(int id);

    void declineInvite(int id);

    void removeFriend(int id);

    List<FriendResponseDto> readFriends(int limit, int offset);

    List<FriendResponseDto> readRequests(int limit, int offset);

}
