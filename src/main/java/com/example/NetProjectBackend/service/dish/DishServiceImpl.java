package com.example.NetProjectBackend.service.dish;


import com.example.NetProjectBackend.dao.DishDao;
import com.example.NetProjectBackend.models.Dish;
import com.example.NetProjectBackend.models.Ingredient;
import com.example.NetProjectBackend.models.Kitchenware;
import com.example.NetProjectBackend.models.dto.dish.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<DishIngredient> addIngredient(DishIngredient dishIngredient) {
        List<DishIngredient> check = dishDao.checkIngredient(dishIngredient.getDish(), dishIngredient.getIngredient());
        if (check.size() > 0)
            return dishDao.updateIngredient(check.get(0).getId(), dishIngredient.getAmount());
        return dishDao.createDishIngredient(dishIngredient);

    }

    @Override
    public List<DishIngredient> removeIngredient(int id) {
        return dishDao.removeIngredient(id);
    }

    @Override
    public List<DishKitchenware> addKitchenware(DishKitchenware dishKitchenware) {
        List<DishKitchenware> kitchenware = dishDao.checkKitchenware(dishKitchenware);
        if (kitchenware.size() > 1) return kitchenware;
        return dishDao.createDishKitchenware(dishKitchenware);

    }

    @Override
    public List<DishKitchenware> removeKitchenware(int id) {
        return dishDao.removeKitchenware(id);
    }

    @Override
    public List<Dish> readList(int limit, int page, boolean desc, String title, String category) {
        if (title == null) title = "%";
        else title = "%"+title+"%";
        if (category == null) category = "%";
        else category = "%"+category+"%";
        DishSearch search = new DishSearch(
                limit,
                page,
                limit*page,
                desc,
                title,
                category
        );
        return dishDao.readList(search);
    }

    @Override
    public DishView getDish(int id) {
        DishView dishView = new DishView(
                dishDao.soloReadDish(id),
                null,
                null,
                null,
                null,
                false,
                false,
                0
        );
        return dishView;
    }
}
