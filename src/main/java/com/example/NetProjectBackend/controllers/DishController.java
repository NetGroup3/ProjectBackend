package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.models.dto.dish.*;
import com.example.NetProjectBackend.models.entity.Comment;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.models.entity.Label;
import com.example.NetProjectBackend.service.DishService;
import com.example.NetProjectBackend.service.Paginator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    @PostMapping
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

    @DeleteMapping
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> deleteDish(@RequestParam int id) {
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

    @DeleteMapping(path = "/kitchenware")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity<?> removeKitchenware(@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeKitchenware(id));
    }

    @GetMapping("/page")
    public ResponseEntity<?> searchDishList(
            @RequestParam int limit,
            @RequestParam int page,
            @RequestParam(required = false) boolean desc,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
            ) {
        return ResponseEntity.ok(dishService.readList(limit, page, desc, title, category, userId));
    }


    @GetMapping
    public ResponseEntity<?> getDish(
            @RequestParam int id,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        log.info(String.valueOf(id));
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


    //http://localhost:8081/dish/ingredients?limit=20&page=0&values=1,2,3,4
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
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getComments(@RequestParam int dishId, int pageNo, int perPage) {
        Paginator.PaginatedResponse res = dishService.getPaginatedComments(dishId, pageNo, perPage);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createComment (@RequestBody Comment comment) {  //@CurrentSecurityContext(expression="authentication.principal.id") Integer userId
        return ResponseEntity.ok(dishService.createComment(comment));
    }

    /*@DeleteMapping("/comment")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteComment (
            @RequestParam int comment,
            @CurrentSecurityContext(expression="authentication.principal.id") Integer userId
    ) {
        return ResponseEntity.ok(dishService.deleteComment(comment, userId));
    }*/

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

}
