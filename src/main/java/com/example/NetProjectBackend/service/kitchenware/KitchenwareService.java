package com.example.NetProjectBackend.service.kitchenware;

import com.example.NetProjectBackend.models.Kitchenware;

import java.util.List;

public interface KitchenwareService {
    int create(Kitchenware kitchenware);

    Kitchenware read(int id);

    void update(Kitchenware kitchenware);

    void delete(int id);

    List<Kitchenware> readPage(int limit, int offset);

    List<Kitchenware> readSearchPage(int limit, int offset, String key, String category, String sortedBy);
}