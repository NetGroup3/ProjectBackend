package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Friend;
import com.example.NetProjectBackend.models.dto.MessageResponse;
import com.example.NetProjectBackend.service.friend.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/send-invite")
    public ResponseEntity<?> addFriend(@RequestBody Friend friend) {
        try {
            friendService.addFriend(friend);
            log.info("Invite sent");
            return ResponseEntity.ok(200);
        } catch (Exception e) {
            log.error("Failed to send invitation");
            log.error(String.valueOf(friend));
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Failed to send invitation "));
        }
    }

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
                    .body(new MessageResponse("Error: Accept invite failed"));
        }
    }

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
                    .body(new MessageResponse("Error: Decline invite failed"));
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
                    .body(new MessageResponse("Error: Remove friend failed"));
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<?> readFriends(@RequestParam int id) {
        try {
            return ResponseEntity.ok(friendService.readFriends(id));
        } catch (Exception e) {
            log.error("Read friends failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: read friends failed"));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<?> readRequests(@RequestParam int id) {
        try {
            return ResponseEntity.ok(friendService.readRequests(id));
        } catch (Exception e) {
            log.error("Read requests failed");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: read requests failed"));
        }
    }

}
