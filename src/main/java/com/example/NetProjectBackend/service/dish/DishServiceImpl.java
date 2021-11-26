package com.example.NetProjectBackend.service.dish;


import com.example.NetProjectBackend.dao.DishDao;
import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.dto.dish.DishIngredient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DishServiceImpl  implements DishService {

    private final DishDao dishDao;

    @Override
    public List<Dish> createDish(Dish dish) {
        return dishDao.create(dish);
    }

    @Override
    public List<Dish> deleteDish(int id) {
        return dishDao.delete(id);
    }

    @Override
    public int setActive(int id, boolean active) {
        System.out.println(dishDao.setActive(id, active));
        return dishDao.setActive(id, active);
    }

    @Override
    public ResponseEntity<?> addIngredient(DishIngredient dishIngredient) {
        List<DishIngredient> check = dishDao.checkIngredient(dishIngredient.getDish(), dishIngredient.getIngredient());
        if (check.size() > 0)
            return ResponseEntity.ok(dishDao.updateIngredient(check.get(0).getId(), dishIngredient.getAmount()));
        return ResponseEntity.ok(dishDao.createDishIngredient(dishIngredient));

    }

    @Override
    public List<DishIngredient> removeIngredient(int id) {
        return dishDao.removeIngredient(id);
    }
}
