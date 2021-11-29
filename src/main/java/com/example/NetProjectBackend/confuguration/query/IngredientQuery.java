package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class IngredientQuery {
    @Value("${ingredient.insert}")
    private String insert;

    @Value("${ingredient.select}")
    private String select;

    @Value("${ingredient.select_page}")
    private String selectPage;

    @Value("${ingredient.update}")
    private String update;

    @Value("${ingredient.delete}")
    private String delete;

    @Value("${ingredient.select_search_page_order_by_id}")
    private String SelectSearchPageById;

    @Value("${ingredient.select_search_page_order_by_title}")
    private String SelectSearchPageByTitle;

    @Value("${ingredient.select_search_page_order_by_category}")
    private String SelectSearchPageByCategory;
}
