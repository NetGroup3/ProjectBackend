package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.service.ingredient.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/ingredient")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<?> readIngredient(@RequestBody int id) {
        return ResponseEntity.ok(ingredientService.read(id));
    }

    @PostMapping("/ingredient")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient) {
        ingredient.set_active(true);
        ingredientService.create(ingredient);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/ingredient")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.update(ingredient);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/ingredient")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteIngredient(@RequestBody int id) {
        ingredientService.delete(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/ingredient/page")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<?> readIngredientPage(@RequestBody int limit, @RequestBody int offset) {
        return ResponseEntity.ok(ingredientService.readPage(limit, offset));
    }

}
