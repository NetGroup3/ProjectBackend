package com.example.NetProjectBackend.confuguration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:link.properties")
public class LinkConfig {

    @Value("${link.url}")
    private String url;

    @Value("${link.url.login}")
    private String login;

    @Value("${link.url.verify}")
    private String verifyUrl;

    @Value("${link.url.recovery}")
    private String recoveryUrl;
}
