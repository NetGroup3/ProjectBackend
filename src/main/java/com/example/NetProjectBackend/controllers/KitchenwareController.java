package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.service.kitchenware.KitchenwareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class KitchenwareController {

    private final KitchenwareService kitchenwareService;

    public KitchenwareController(KitchenwareService kitchenwareService) {
        this.kitchenwareService = kitchenwareService;
    }


    @GetMapping(path = "/kitchenware/{id}")
    public ResponseEntity<?> readKitchenware(@PathVariable int id) {
        return ResponseEntity.ok(kitchenwareService.read(id));
    }

    @PostMapping("/kitchenware")
    public ResponseEntity<?> createKitchenware(@RequestBody Kitchenware kitchenware) {
        kitchenwareService.create(kitchenware);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/kitchenware")
    public ResponseEntity<?> updateKitchenware(@RequestBody Kitchenware kitchenware) {
        kitchenwareService.update(kitchenware);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/kitchenware")
    public ResponseEntity<?> deleteKitchenware(@RequestParam int id) {
        kitchenwareService.delete(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/kitchenware/page")
    public ResponseEntity<?> readKitchenwarePage(@RequestParam int limit, @RequestParam int page) {
        return ResponseEntity.ok(kitchenwareService.readPage(limit, limit * page));
    }

    @GetMapping("/kitchenware/search")
    public ResponseEntity<?> readSearchPage(@RequestParam int limit,                            //necessary in request
                                            @RequestParam int page,                             //necessary in request
                                            @RequestParam(defaultValue = "") String key,        //optional(user input), empty field possible
                                            @RequestParam(defaultValue = "") String category,   //optional(dish, cooking tool...), empty field possible
                                            @RequestParam(defaultValue = "id") String sortedBy  //necessary(id, title, category)
    ) {
        return ResponseEntity.ok(kitchenwareService.readSearchPage(limit, limit * page, key, category, sortedBy));
    }

}