package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.userstock.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping(path = "/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readStock(@RequestParam int limit,
                                       @RequestParam int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.readStock(userId, limit, limit * page));
    }

    @DeleteMapping(path = "/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteStockElement(@RequestParam int id) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        userStockService.deleteStockElement(userId, id);
        return ResponseEntity.ok("Successfully deleted from the stock");
    }

    @PostMapping("/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createStockElement(@RequestParam String ingredient,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.createStockElement(userId, ingredient, amount);
        if (result.equals("Not found ingredient"))
            return ResponseEntity.ok("Not found ingredient");
        else if (result.equals("Already exist in your stock"))
            return ResponseEntity.ok("Already exist in your stock");
        else
            return ResponseEntity.ok(userStockService.readStockElement(userId, ingredient));
    }

    @PatchMapping("/stock")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateStockElement(@RequestParam String ingredient,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.updateStockElement(userId, ingredient, amount);
        if (result.equals("Not found in your stock"))
            return ResponseEntity.ok("Not found in your stock");
        else
            return ResponseEntity.ok(userStockService.readStockElement(userId, ingredient));
    }
}
