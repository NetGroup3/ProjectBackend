package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.FriendDao;
import com.example.NetProjectBackend.models.dto.FriendResponseDto;
import com.example.NetProjectBackend.models.enums.EFriendStatus;
import com.example.NetProjectBackend.service.FriendService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void addFriend(int recipientId) {
        int senderId = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        String status = EFriendStatus.AWAITING.name();
        OffsetDateTime timestamp = OffsetDateTime.now();
        friendDao.create(recipientId, senderId, status, timestamp);
    }

    @Override
    public void acceptInvite(int id) {
        String status = EFriendStatus.FRIEND.name();
        friendDao.update(status, id);
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
    public List<FriendResponseDto> readFriends(int limit, int offset) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String status = EFriendStatus.FRIEND.name();
        return friendDao.readFriends(status, limit, offset, userId);
    }

    @Override
    public List<FriendResponseDto> readRequests(int limit, int offset) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String status = EFriendStatus.AWAITING.name();
        return friendDao.readRequests(status, limit, offset, userId);
    }

}
