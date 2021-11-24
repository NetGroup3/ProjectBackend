package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Kitchenware;

import java.util.List;

public interface KitchenwareDao {
    int create(Kitchenware kitchenware);

    Kitchenware read(int id);

    void update(Kitchenware kitchenware);

    void delete(int id);

    List<Kitchenware> readPage(int limit, int offset);
}
