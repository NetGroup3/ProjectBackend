package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.FriendRequestDto;
import com.example.NetProjectBackend.models.dto.MessageResponseDto;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.friend.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * {
     *  "recipientId":
     * }
     */
    @PostMapping("/send-invite")
    public void addFriend(@RequestBody Friend friend) {
        friend.setSenderId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        friendService.addFriend(friend);
        log.info("Invite sent");
    }

    /**
     * {
     *  request id
     *  "id":
     * }
     */
    @PutMapping("/accept-invite")
    public void acceptInvite(@RequestBody Friend friend) {
        friendService.acceptInvite(friend);
        log.info("Invite accepted");
    }

    /**
     * {
     *  request id
     *  "id":
     * }
     */
    @DeleteMapping("/decline-invite")
    public void declineInvite(@RequestBody Friend friend) {
        friendService.declineInvite(friend.getId());
        log.info("Invite declined");
    }

    @DeleteMapping("/remove-friend")
    public void removeFriend(@RequestBody Friend friend) {
            friendService.removeFriend(friend.getId());
            log.info("Friend removed");
    }

    /**
     * {
     * "limit":
     * "offset":
     * }
     */
    @GetMapping("/friends")
    public ResponseEntity<?> readFriends(@RequestBody FriendRequestDto friendRequestDto) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(friendService.readFriends(friendRequestDto, userId));
    }

    /**
     * {
     * "limit":
     * "offset":
     * }
     */
    @GetMapping("/requests")
    public ResponseEntity<?> readRequests(@RequestBody FriendRequestDto friendRequestDto) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(friendService.readRequests(friendRequestDto, userId));
    }

}
