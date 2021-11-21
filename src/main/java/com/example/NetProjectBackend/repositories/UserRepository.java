package com.example.NetProjectBackend.repositories;

import java.util.List;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.UserListRequest;

public interface UserRepository {
    
    public User create(User user);
    public User readById(int id);
    public User readByEmail(String email);
    public User update(User user);
    public User delete(int id);
    public List<User> getAllSuitable(UserListRequest req);
}
