package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:query.properties")
public class KitchenwareConfig {
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
}
