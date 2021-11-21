package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;

import java.util.List;

public interface UserRepository {
    public User create(User user);
    public User readById(int id);
    public User readByEmail(String email);
    public User readByName(String name);
    public User update(User user);
    public User delete(int id);
    public List<User> getAll();
    void changeStatus(EStatus status, int id);
    void changePassword(User user, String password);
    User updatePassword(String password, int id);
}
