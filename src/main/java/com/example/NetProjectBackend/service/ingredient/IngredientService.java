package com.example.NetProjectBackend.service.ingredient;

import com.example.NetProjectBackend.models.Ingredient;
import org.springframework.http.ResponseEntity;

public interface IngredientService {
    ResponseEntity<?> create(Ingredient ingredient);

    ResponseEntity<?> read(int id);

    ResponseEntity<?> update(Ingredient ingredient);

    ResponseEntity<?> delete(int id);

    ResponseEntity<?> readPage(int limit, int offset);

    ResponseEntity<?> readSearchPage(int limit, int offset, String key, String category, String sortedBy);
}
