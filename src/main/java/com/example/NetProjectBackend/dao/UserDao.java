package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.UserListRequest;

import java.util.List;

public interface UserDao {
    List<User> getAllSuitable(UserListRequest req);
    int create(User client);
    User readById(int id);
    User readByEmail(String Email);
    void update(User client);
    void delete(int id);
}
