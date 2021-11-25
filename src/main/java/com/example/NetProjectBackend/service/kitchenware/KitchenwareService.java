package com.example.NetProjectBackend.service.kitchenware;

import com.example.NetProjectBackend.models.Kitchenware;
import org.springframework.http.ResponseEntity;

public interface KitchenwareService {
    ResponseEntity<?> create(Kitchenware kitchenware);

    ResponseEntity<?> read(int id);

    ResponseEntity<?> update(Kitchenware kitchenware);

    ResponseEntity<?> delete(int id);

    ResponseEntity<?> readPage(int limit, int offset);

    ResponseEntity<?> readSearchPage(int limit, int offset, String key, String category, String sortedBy);
}