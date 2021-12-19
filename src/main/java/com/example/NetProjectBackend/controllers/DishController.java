package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.PaginatedResponse;
import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.entity.Label;
import com.example.NetProjectBackend.service.DishService;
import com.example.NetProjectBackend.service.Paginator;
import com.example.NetProjectBackend.service.UserSessionService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final UserSessionService userSessionService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> createDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.createDish(dish));
    }

    @PostMapping("/full")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> createDishFromList(
        @RequestBody DishWrapperDto dishWrapperDto
    ) {
        return ResponseEntity.ok(dishService.createDishFromList(dishWrapperDto));
    }

    @PutMapping("/full")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> updateDishFromList(
            @RequestBody DishWrapperDto dishWrapperDto
    ) {
        return ResponseEntity.ok(dishService.updateDishFromList(dishWrapperDto));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> deleteDish(@RequestParam int id) {
        System.out.println(id);
        return ResponseEntity.ok(dishService.deleteDish(id));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> updateDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.editDish(dish));
    }

    @PutMapping("/active")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> setActiveDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.setActive(dish.getId(), dish.isActive()));
    }

    @PostMapping("/ingredient")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> addIngredient(@RequestBody DishIngredientDto dishIngredientDto) {
        return ResponseEntity.ok(dishService.addIngredient(dishIngredientDto));
    }

    @DeleteMapping("/ingredient")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> removeIngredient(@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeIngredient(id));
    }


    @PostMapping("/kitchenware")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> addKitchenware(@RequestBody DishKitchenwareDto dishKitchenwareDto) {
        return ResponseEntity.ok(dishService.addKitchenware(dishKitchenwareDto));
    }

    @DeleteMapping("/kitchenware")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> removeKitchenware(@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeKitchenware(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> searchDishList(
            @RequestParam int limit,
            @RequestParam int page,
            @RequestParam(required = false) boolean desc,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category
            ) {
        int userId;
        try {
            userId = userSessionService.getUserIdFromSession();
        } catch (ExpiredJwtException | ClassCastException ignored) {
            userId = 0;
        }
        return ResponseEntity.ok(dishService.readList(limit, page, desc, title, category, userId));
    }


    @GetMapping()
    public ResponseEntity<?> getDish(
            @RequestParam int id
    ) {
        int userId;
        try {
            userId = userSessionService.getUserIdFromSession();
        } catch (ExpiredJwtException | ClassCastException ignored) {
            userId = 0;
        }
        return ResponseEntity.ok(dishService.getDish(id, userId));
    }

    @GetMapping("/recommend")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getRecommend(
            @RequestParam int limit,
            @RequestParam int page,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        return ResponseEntity.ok(dishService.getRecommend(userId, limit, page));
    }


    @GetMapping(path="/ingredients")
    public ResponseEntity<?> getWithIngredients (
            @RequestParam List<Integer> values,
            @RequestParam int limit,
            @RequestParam int page
    ) {
        return ResponseEntity.ok(dishService.getWithIngredients(values, limit, page));
    }


    @PostMapping("/favourite")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addFavourite (
            @RequestBody FavouriteDto favouriteDto,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        return ResponseEntity.ok(dishService.addFavourite(userId, favouriteDto.getDish()));
    }

    @GetMapping("/favourite")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getFavourite (
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        return ResponseEntity.ok(dishService.getFavourite(userId));
    }

    @DeleteMapping("/favourite")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> removeFavourite (
            @RequestParam Integer dish,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        return ResponseEntity.ok(dishService.removeFavourite(userId, dish));
    }

    @PostMapping("/like")
    public ResponseEntity<?> addLike (@RequestBody DishLikeDto like) {
        return ResponseEntity.ok(dishService.setLike(like.getDish()));
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getComments(@RequestParam int dishId, int pageNo, int perPage) {
        List<CommentPaginatedDto> list = dishService.getPaginatedComments(dishId, pageNo, perPage);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment (@RequestBody Comment comment) {
        Integer userId = null;
        try {
            userId = userSessionService.getUserIdFromSession();
        } catch (ExpiredJwtException | ClassCastException ignored) {}
        comment.setUserId(userId);
        return ResponseEntity.ok(dishService.createComment(comment));
    }

    @PostMapping("/label/edit")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> createLabel (@RequestBody Label label) {
        return ResponseEntity.ok(dishService.createLabel(label));
    }

    @PutMapping("/label/edit")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> editLabel (@RequestBody Label label) {
        return ResponseEntity.ok(dishService.editLabel(label));
    }

    @GetMapping("/label/edit")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> getLabels (
            @RequestParam int limit,
            @RequestParam int page
    ) {
        return ResponseEntity.ok(dishService.getLabels(limit, page));
    }

    @DeleteMapping("/label/edit")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> deleteLabel (@RequestParam int id) {
        return ResponseEntity.ok(dishService.deleteLabel(id));
    }

    @PostMapping("/label")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> addLabel (@RequestBody DishLabelDto label) {
        return ResponseEntity.ok(dishService.addLabel(label));
    }

    @DeleteMapping("/label")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> removeLabel (@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeLabel(id));
    }

    @Secured("USER")
    @GetMapping("/pages")
    public int howPages (@RequestParam int limit){
        return dishService.getPages(limit);
    }

}
