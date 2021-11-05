package com.example.NetProjectBackend.repositories;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.NetProjectBackend.models.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private Map<Integer, User> temporaryStorage;
    private int nextId;

    UserRepositoryImpl() {
        temporaryStorage = new LinkedHashMap<>();
        nextId = 0;
    }

    @Override
    public User create(User user) {
        user.setId(nextId);
        temporaryStorage.put(nextId, user);
        nextId ++;
        return temporaryStorage.get(nextId - 1);
    }

    @Override
    public User read(int id) {
        return temporaryStorage.get(id);
    }

    @Override
    public User update(User user) {
        User u = temporaryStorage.get(user.getId());
        if (u == null) {
            return null;
        }
        temporaryStorage.put(user.getId(), user);
        return temporaryStorage.get(user.getId());
    }

    @Override
    public User delete(int id) {
        User user = temporaryStorage.get(id);
        if (user == null) {
            return null;
        }
        temporaryStorage.remove(id);
        return user;
    }
}
