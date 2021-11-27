package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;
import com.example.NetProjectBackend.models.dto.dish.DishKitchenware;
import com.example.NetProjectBackend.service.dish.DishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    @PostMapping
    public ResponseEntity<?> createDish(@RequestBody Dish dish) {
        System.out.println(dish);
        return ResponseEntity.ok(dishService.createDish(dish));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDish(@RequestParam int id) {
        return ResponseEntity.ok(dishService.deleteDish(id));
    }

    @PatchMapping("/active")
    public ResponseEntity<?> setActiveDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.setActive(dish.getId(), dish.isActive()));
    }

    @PostMapping("/ingredient")
    public ResponseEntity<?> addIngredient(@RequestBody DishIngredient dishIngredient) {
        return  ResponseEntity.ok(dishService.addIngredient(dishIngredient));
    }

    @DeleteMapping("/ingredient")
    public ResponseEntity<?> removeIngredient(@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeIngredient(id));
    }


    @PostMapping("/kitchenware")
    public ResponseEntity<?> addKitchenware(@RequestBody DishKitchenware dishKitchenware) {
        return ResponseEntity.ok(dishService.addKitchenware(dishKitchenware));
    }

    @DeleteMapping(path = "/kitchenware")
    public ResponseEntity<?> removeKitchenware(@RequestParam int id) {
        return ResponseEntity.ok(dishService.removeKitchenware(id));
    }

    // Осталось
    @GetMapping
    public ResponseEntity<?> getDish(@RequestParam int id) {
        return ResponseEntity.ok(200);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getDishList() {
        return ResponseEntity.ok(200);
    }

}
