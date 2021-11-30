package com.example.NetProjectBackend.service.kitchenware;

import com.example.NetProjectBackend.dao.KitchenwareDao;
import com.example.NetProjectBackend.models.Kitchenware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class KitchenwareServiceImpl implements KitchenwareService {

    private final KitchenwareDao kitchenwareDao;

    public KitchenwareServiceImpl(KitchenwareDao kitchenwareDao) {
        this.kitchenwareDao = kitchenwareDao;
    }

    @Override
    public ResponseEntity<?> create(Kitchenware kitchenware) {
        kitchenware.setActive(true);
        return ResponseEntity.ok(kitchenwareDao.create(kitchenware));
    }

    @Override
    public ResponseEntity<?> read(int id) {
        return ResponseEntity.ok(kitchenwareDao.read(id));
    }

    @Override
    public ResponseEntity<?> update(Kitchenware kitchenware) {
        kitchenwareDao.update(kitchenware);
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        kitchenwareDao.delete(id);
        return ResponseEntity.ok(200);
    }

    @Override
    public ResponseEntity<?> readPage(int limit, int offset) {
        if (limit > 100) limit = 100;
        return ResponseEntity.ok(kitchenwareDao.readPage(limit, offset));
    }

    @Override
    public ResponseEntity<?> readSearchPage(int limit, int offset, String key, String category, String sortedBy) {
        if (limit > 100) limit = 100;
        return ResponseEntity.ok(kitchenwareDao.readSearchPage(limit, offset, key, category, sortedBy));
    }
}
