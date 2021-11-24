package com.example.NetProjectBackend.confuguration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:link.properties")
public class LinkConfig {
    @Value("${link.url}")
    private String url;

    @Value("${link.code}")
    private String code;
}
