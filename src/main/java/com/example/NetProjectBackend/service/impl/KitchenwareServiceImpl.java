package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.service.KitchenwareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class KitchenwareServiceImpl implements KitchenwareService {

    private final KitchenwareDao kitchenwareDao;

    public KitchenwareServiceImpl(KitchenwareDao kitchenwareDao) {
        this.kitchenwareDao = kitchenwareDao;
    }

    @Override
    public int create(Kitchenware kitchenware) {
        kitchenware.setActive(true);
        return kitchenwareDao.create(kitchenware);
    }

    @Override
    public Kitchenware read(int id) {
        return kitchenwareDao.read(id);
    }

    @Override
    public void update(Kitchenware kitchenware) {
        kitchenwareDao.update(kitchenware);
    }

    @Override
    public void delete(int id) {
        kitchenwareDao.delete(id);
    }

    @Override
    public List<Kitchenware> readPage(int limit, int offset) {
        if (limit > 100) limit = 100;
        return kitchenwareDao.readPage(limit, offset);
    }

    @Override
    public List<Kitchenware> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        return kitchenwareDao.readSearchPage(limit, offset, key, category, sortedBy);
    }
}
