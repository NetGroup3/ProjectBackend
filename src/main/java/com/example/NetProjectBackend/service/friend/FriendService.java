package com.example.NetProjectBackend.service.friend;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequest;
import com.example.NetProjectBackend.models.dto.FriendResponse;

import java.util.List;

public interface FriendService {
    void addFriend(Friend friend);

    void acceptInvite(Friend friend);

    void declineInvite(int id);

    void removeFriend(int id);

    List<FriendResponse> readFriends(FriendRequest friendRequest, int id);

    List<FriendResponse> readRequests(FriendRequest friendRequest, int id);

}
