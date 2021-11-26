package com.example.NetProjectBackend.confuguration.query;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:query.properties")
public class UserStockConfig {
    @Value("${user_stock.select}")
    private String select;
}
