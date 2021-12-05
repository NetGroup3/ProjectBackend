package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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
     * "recipientId":
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
     * request id
     * }
     */
    @PutMapping("/accept-invite")
    public void acceptInvite(@RequestParam int id) {
        friendService.acceptInvite(id);
        log.info("Invite accepted");
    }

    /**
     * {
     * request id
     * }
     */
    @DeleteMapping("/decline-invite")
    public void declineInvite(@RequestParam int id) {
        friendService.declineInvite(id);
        log.info("Invite declined");
    }

    @DeleteMapping("/remove-friend")
    public void removeFriend(@RequestParam int id) {
            friendService.removeFriend(id);
            log.info("Friend removed");
    }

    @GetMapping("/friends")
    public ResponseEntity<?> readFriends(@RequestParam int limit,
                                         @RequestParam int offset
    ) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(friendService.readFriends(limit, offset, userId));
    }

    @GetMapping("/requests")
    public ResponseEntity<?> readRequests(@RequestParam int limit,
                                          @RequestParam int offset
    ) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(friendService.readRequests(limit, offset, userId));
    }

}
