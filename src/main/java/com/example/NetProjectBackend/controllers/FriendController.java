package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.FriendResponseDto;
import com.example.NetProjectBackend.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/send-invite")
    public void addFriend(@RequestParam int id) {
        friendService.addFriend(id);
        log.info("Invite sent");
    }

    @PutMapping("/accept-invite")
    public void acceptInvite(@RequestParam int id) {
        friendService.acceptInvite(id);
        log.info("Invite accepted");
    }

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
    public List<FriendResponseDto> readFriends(@RequestParam int limit,
                                               @RequestParam int offset) {
        return friendService.readFriends(limit, offset);
    }

    @GetMapping("/requests")
    public List<FriendResponseDto> readRequests(@RequestParam int limit,
                                                @RequestParam int offset) {
        return friendService.readRequests(limit, offset);
    }

}
