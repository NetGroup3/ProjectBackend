package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    int create(User client);
    User read(int id);
    void update(User client);
    void delete(int id);
}
