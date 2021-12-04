package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class VerifyQuery {
    @Value("${verify.insert}")
    private String insert;

    @Value("${verify.select_by_id}")
    private String selectById;

    @Value("${verify.select_by_code}")
    private String selectByCode;

    @Value("${verify.update}")
    private String update;

    @Value("${verify.delete}")
    private String delete;
}
