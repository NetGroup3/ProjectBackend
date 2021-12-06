package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.UserStockElement;
import com.example.NetProjectBackend.models.dto.StockAddDto;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.UserStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<UserStockElement> readStock(@RequestParam int limit,
                                            @RequestParam int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return userStockService.readStock(userId, limit, limit * page);
    }

    @GetMapping("/ingredients")
    @PreAuthorize("hasAuthority('USER')")
    public List<Ingredient> readIngredients(@RequestParam(defaultValue = "100") int limit ,
                                            @RequestParam(defaultValue = "0") int page) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return userStockService.readIngredients(userId, limit, limit * page);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    public void deleteStockElement(@RequestParam int ingredientId) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        userStockService.deleteStockElement(userId, ingredientId);
/*        if (userStockService.deleteStockElement(userId, ingredientId) == "Not found")
            return ResponseEntity.ok("Not found in your stock");
        else
            return ResponseEntity.ok("Successfully deleted from the stock");*/
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createStockElement(@RequestBody StockAddDto stockAddDto) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.createStockElement(userId, stockAddDto.getIngredientId(), stockAddDto.getAmount());
        if (result.equals("Not found ingredient"))
            return ResponseEntity.ok("Not found ingredient");
        else if (result.equals("Already exist in your stock"))
            return ResponseEntity.ok("Already exist in your stock");
        else
          return ResponseEntity.ok(userStockService.readStockElement(userId, stockAddDto.getIngredientId()));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateStockElement(@RequestBody StockAddDto stockAddDto) {
        int userId = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        String result = userStockService.updateStockElement(userId, stockAddDto.getIngredientId(), stockAddDto.getAmount());
        if (result.equals("Not found"))
            return ResponseEntity.ok("Not found in your stock");
        else
            return ResponseEntity.ok(userStockService.readStockElement(userId, stockAddDto.getIngredientId()));
    }

    @GetMapping("/search")
    public List<UserStockElement> readSearchPage(@RequestParam int limit,
                                                @RequestParam int page,
                                                @RequestParam(defaultValue = "") String key,        //optional(user input), empty field possible
                                                @RequestParam(defaultValue = "") String category,   //optional(dish, cooking tool...), empty field possible
                                                @RequestParam(defaultValue = "id") String sortedBy,  //necessary(id, title, category, description)
                                                @CurrentSecurityContext(expression="authentication.principal.id") int userId
    ) {
        return userStockService.readSearchPage(limit, limit * page, key, category, sortedBy, userId);
    }

}
