package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class KitchenwareQuery {
    @Value("${kitchenware.insert}")
    private String insert;

    @Value("${kitchenware.select}")
    private String select;

    @Value("${kitchenware.select_page}")
    private String selectPage;

    @Value("${kitchenware.update}")
    private String update;

    @Value("${kitchenware.delete}")
    private String delete;

    @Value("${kitchenware.select_search_page_order_by_id}")
    private String SelectSearchPageById;

    @Value("${kitchenware.select_search_page_order_by_title}")
    private String SelectSearchPageByTitle;

    @Value("${kitchenware.select_search_page_order_by_category}")
    private String SelectSearchPageByCategory;
}



