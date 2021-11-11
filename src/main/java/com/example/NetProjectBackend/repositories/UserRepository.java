package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.models.User;

public interface UserRepository {
    public User create(User user);
    public User read(int id);
    public User update(User user);
    public User delete(int id);
    public List<User> getAll();
}
