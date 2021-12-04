package com.example.NetProjectBackend.service.friend;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendResponse;

import java.util.List;

public interface FriendService {
    void addFriend(Friend friend);

    void acceptInvite(int id);

    void declineInvite(int id);

    void removeFriend(int id);

    List<FriendResponse> readFriends(int limit, int offset, int id);

    List<FriendResponse> readRequests(int limit, int offset, int id);

}
