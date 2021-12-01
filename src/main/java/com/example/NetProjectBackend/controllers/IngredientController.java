package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping(path = "/ingredient")
    public ResponseEntity<?> readIngredient(@RequestParam int id) {
        return ResponseEntity.ok(ingredientService.read(id));
    }

    @PostMapping("/ingredient")
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient) {
        ingredient.setActive(true);
        return ResponseEntity.ok(ingredientService.create(ingredient));
    }

    @PutMapping("/ingredient")
    public void updateIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.update(ingredient);
    }

    @DeleteMapping("/ingredient")
    public void deleteIngredient(@RequestParam int id) {
        ingredientService.delete(id);
    }

    @GetMapping("/ingredient/page")
    public ResponseEntity<?> readIngredientPage(@RequestParam int limit, @RequestParam int page) {
        return ResponseEntity.ok(ingredientService.readPage(limit,limit*page));
    }

    ///////////////////////////
    @GetMapping("/ingredient/search")
    public ResponseEntity<?> readSearchPage(@RequestParam int limit,                            //necessary in request
                                            @RequestParam int page,                             //necessary in request
                                            @RequestParam(defaultValue = "") String key,        //optional(user input), empty field possible
                                            @RequestParam(defaultValue = "") String category,   //optional(dish, cooking tool...), empty field possible
                                            @RequestParam(defaultValue = "id") String sortedBy  //necessary(id, title, category)
    ) {
        return ResponseEntity.ok(ingredientService.readSearchPage(limit, limit * page, key, category, sortedBy));
    }

}
