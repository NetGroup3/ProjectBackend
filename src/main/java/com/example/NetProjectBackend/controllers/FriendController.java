package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.FriendResponseDto;
import com.example.NetProjectBackend.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('USER')")
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
    }

    @PutMapping("/accept-invite")
    public void acceptInvite(@RequestParam int id) {
        friendService.acceptInvite(id);
    }

    @DeleteMapping("/decline-invite")
    public void declineInvite(@RequestParam int id) {
        friendService.declineInvite(id);
    }

    @DeleteMapping("/remove-friend")
    public void removeFriend(@RequestParam int id) {
        friendService.removeFriend(id);
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
