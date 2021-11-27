package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:query.properties")
public class DishConfig {

}
