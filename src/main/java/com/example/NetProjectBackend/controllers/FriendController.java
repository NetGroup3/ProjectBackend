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
    public ResponseEntity<?> addFriend(@RequestBody Friend friend) {
        try {
            friend.setSenderId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            friendService.addFriend(friend);
            log.info("Invite sent");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Failed to send invitation");
            log.error(String.valueOf(friend));
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Failed to send invitation "));
        }
    }

    /**
     * {
     *  request id
     *  "id":
     * }
     */
    @PutMapping("/accept-invite")
    public ResponseEntity<?> acceptInvite(@RequestBody Friend friend) {
        try {
            friendService.acceptInvite(friend);
            log.info("Invite accepted");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Accept invite failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Accept invite failed"));
        }
    }

    /**
     * {
     *  request id
     *  "id":
     * }
     */
    @DeleteMapping("/decline-invite")
    public ResponseEntity<?> declineInvite(@RequestBody Friend friend) {
        try {
            friendService.declineInvite(friend.getId());
            log.info("Invite declined");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Decline invite failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Decline invite failed"));
        }
    }

    @DeleteMapping("/remove-friend")
    public ResponseEntity<?> removeFriend(@RequestBody Friend friend) {
        try {
            friendService.removeFriend(friend.getId());
            log.info("Friend removed");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Remove friend failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: Remove friend failed"));
        }
    }

    /**
     * {
     * "limit":
     * "offset":
     * }
     */
    @GetMapping("/friends")
    public ResponseEntity<?> readFriends(@RequestBody FriendRequestDto friendRequestDto) {
        try {
            int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            return ResponseEntity.ok(friendService.readFriends(friendRequestDto, userId));
        } catch (Exception e) {
            log.error("Read friends failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: read friends failed"));
        }
    }

    /**
     * {
     * "limit":
     * "offset":
     * }
     */
    @GetMapping("/requests")
    public ResponseEntity<?> readRequests(@RequestBody FriendRequestDto friendRequestDto) {
        try {
            int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            return ResponseEntity.ok(friendService.readRequests(friendRequestDto, userId));
        } catch (Exception e) {
            log.error("Read requests failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDto("Error: read requests failed"));
        }
    }

}
