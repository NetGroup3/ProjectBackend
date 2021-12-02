package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user/stock")
public class UserStockController {
    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readStock(@RequestParam int limit,
                                       @RequestParam int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.readStock(userId, limit, limit * page));
    }

    @GetMapping("/ingredients")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readIngredients(@RequestParam int limit,
                                       @RequestParam int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(userStockService.readIngredients(userId, limit, limit * page));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteStockElement(@RequestParam int ingredientId) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        if (userStockService.deleteStockElement(userId, ingredientId) == "Not found")
            return ResponseEntity.ok("Not found in your stock");
        else
            return ResponseEntity.ok("Successfully deleted from the stock");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createStockElement(@RequestParam int ingredientId,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.createStockElement(userId, ingredientId, amount);
        if (result.equals("Not found ingredient"))
            return ResponseEntity.ok("Not found ingredient");
        else if (result.equals("Already exist in your stock"))
            return ResponseEntity.ok("Already exist in your stock");
        else
            return ResponseEntity.ok(userStockService.readStockElement(userId, ingredientId));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateStockElement(@RequestParam int ingredientId,
                                                @RequestParam int amount) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.updateStockElement(userId, ingredientId, amount);
        if (result.equals("Not found"))
            return ResponseEntity.ok("Not found in your stock");
        else
            return ResponseEntity.ok(userStockService.readStockElement(userId, ingredientId));
    }
}
