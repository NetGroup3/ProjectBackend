package com.example.NetProjectBackend.service.friend;

import com.example.NetProjectBackend.dao.FriendDao;
import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequestDto;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;
import com.example.NetProjectBackend.models.enums.EFriendStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class FriendServiceImp implements FriendService {

    private final FriendDao friendDao;

    public FriendServiceImp(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @Override
    public void addFriend(Friend friend) {
        friend.setStatus(EFriendStatus.AWAITING.name());
        friend.setTimestamp(OffsetDateTime.now());
        friendDao.create(friend);
    }

    @Override
    public void acceptInvite(Friend friend) {
        friend.setStatus(EFriendStatus.FRIEND.name());
        friendDao.update(friend.getStatus(), friend.getId());
    }

    @Override
    public void declineInvite(int id) {
        friendDao.delete(id);
    }

    @Override
    public void removeFriend(int id) {
        friendDao.delete(id);
    }

    @Override
    public List<FriendResponseDto> readFriends(FriendRequestDto friendRequestDto, int id) {
        friendRequestDto.setStatus(EFriendStatus.FRIEND.name());
        return friendDao.readFriends(friendRequestDto, id);
    }

    @Override
    public List<FriendResponseDto> readRequests(FriendRequestDto friendRequestDto, int id) {
        friendRequestDto.setStatus(EFriendStatus.AWAITING.name());
        return friendDao.readRequests(friendRequestDto, id);
    }
}
