package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class UserStockQuery {

    private final Map<String, String> query = new HashMap<>();

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

    @Value("${user_stock.select_ingredients_not_present_in_stock}")
    private String selectIngredientNotPresentInStock;

    @Value("${user_stock.select_search_page_order_by_id}")
    private String selectSearchPageById;

    @Value("${user_stock.select_search_page_order_by_title}")
    private String selectSearchPageByTitle;

    @Value("${user_stock.select_search_page_order_by_category}")
    private String selectSearchPageByCategory;

    @Value("${user_stock.select_search_page_order_by_description}")
    private String selectSearchPageByDescription;

    @Value("${user_stock.select_search_page_order_by_amount}")
    private String selectSearchPageByAmount;

    @Value("${user_stock.stock_row}")
    private String selectRows;

    public void setUserSearchPageQuery() {
        query.put("title", this.getSelectSearchPageByTitle());
        query.put("id", this.getSelectSearchPageById());
        query.put("category", this.getSelectSearchPageByCategory());
        query.put("description", this.getSelectSearchPageByDescription());
        query.put("amount", this.getSelectSearchPageByAmount());
    }

}
