package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;

import java.util.List;

public interface UserRepository {
    User create(User user);
    User readById(int id);
    User readByEmail(String email);
    User readByName(String name);
    User update(User user);
    User delete(int id);
    List<User> getAll();
    void changeStatus(EStatus status, int id);
    void changePassword(User user, String password);
    User updatePassword(String password, int id);
}
