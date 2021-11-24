package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.service.ingredient.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping(path = "/ingredient")
    public ResponseEntity<?> readIngredient(@RequestParam int id) {
        System.out.println("OK");

        return ResponseEntity.ok(ingredientService.read(id));
    }

    @PostMapping("/ingredient")
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient) {
        ingredient.set_active(true);
        ingredientService.create(ingredient);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/ingredient")
    public ResponseEntity<?> updateIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.update(ingredient);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/ingredient")
    public ResponseEntity<?> deleteIngredient(@RequestParam int id) {
        ingredientService.delete(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/ingredient/page")
    public ResponseEntity<?> readIngredientPage(@RequestParam int limit, @RequestParam int offset) {
        return ResponseEntity.ok(ingredientService.readPage(limit, offset));
    }

}
