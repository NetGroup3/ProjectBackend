package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class UserQuery {
    @Value("${user.select_all_from_client}")
    private String selectAllFromClient;

    @Value("${user.select_by_id}")
    private String selectById;

    @Value("${user.select_by_email}")
    private String selectByEmail;

    @Value("${user.select_by_name}")
    private String selectByName;

    @Value("${user.insert_into_client_values}")
    private String insertIntoClientValues;

    @Value("${user.update_client}")
    private String updateClient;

    @Value("${user.update_client_image}")
    private String updateClientImage;

    @Value("${user.delete_client}")
    private String deleteClient;

    @Value("${user.update_status}")
    private String updateStatus;

    @Value("${user.update_password}")
    private String updatePassword;

    @Value("${user.select_page}")
    private String selectPage;
}
