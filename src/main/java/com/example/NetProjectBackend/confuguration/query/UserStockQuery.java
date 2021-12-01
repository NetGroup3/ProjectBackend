package com.example.NetProjectBackend.confuguration.query;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class UserStockQuery {
    @Value("${user_stock.select}")
    private String select;

    @Value("${user_stock.delete}")
    private String delete;

    @Value("${user_stock.select_ingredient_id}")
    private String selectIngredientId;

    @Value("${user_stock.insert}")
    private String insert;

    @Value("${user_stock.select_by_userid_and_ingredientid}")
    private String selectByUserIdAndIngredientId;

    @Value("${user_stock.update_by_userid_and_ingredientid}")
    private String updateByUserIdAndIngredientId;



}
