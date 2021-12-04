package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.MessageResponse;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.friend.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addFriend(@RequestBody Friend friend) {
        try {
            friend.setSenderId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            friendService.addFriend(friend);
            log.info("Invite sent");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to send invitation");
            log.error(String.valueOf(friend));
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Failed to send invitation "));
        }
    }

    /**
     * {
     * request id
     * }
     */
    @PutMapping("/accept-invite")
    public ResponseEntity<?> acceptInvite(@RequestParam int id) {
        try {
            friendService.acceptInvite(id);
            log.info("Invite accepted");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Accept invite failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Accept invite failed"));
        }
    }

    /**
     * {
     * request id
     * }
     */
    @DeleteMapping("/decline-invite")
    public ResponseEntity<?> declineInvite(@RequestParam int id) {
        try {
            friendService.declineInvite(id);
            log.info("Invite declined");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Decline invite failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Decline invite failed"));
        }
    }

    @DeleteMapping("/remove-friend")
    public ResponseEntity<?> removeFriend(@RequestParam int id) {
        try {
            friendService.removeFriend(id);
            log.info("Friend removed");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Remove friend failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Remove friend failed"));
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<?> readFriends(@RequestParam int limit,
                                         @RequestParam int offset
    ) {
        try {
            int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            return ResponseEntity.ok(friendService.readFriends(limit, offset, userId));
        } catch (Exception e) {
            log.error("Read friends failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: read friends failed"));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<?> readRequests(@RequestParam int limit,
                                          @RequestParam int offset
    ) {
        try {
            int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
            return ResponseEntity.ok(friendService.readRequests(limit, offset, userId));
        } catch (Exception e) {
            log.error("Read requests failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: read requests failed"));
        }
    }

}
