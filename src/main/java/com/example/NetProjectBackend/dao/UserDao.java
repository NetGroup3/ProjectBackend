package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    int create(User client);
    User readById(int id);
    User readByEmail(String Email);
    void update(User client);
    void delete(int id);
    void changeStatus(EStatus status, int id);
}
