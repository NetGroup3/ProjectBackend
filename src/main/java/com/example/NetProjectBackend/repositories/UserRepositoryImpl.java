package com.example.NetProjectBackend.repositories;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }




    @Override
    public User create(User user) {
        userDao.create(user);
        return userDao.read(user.getId());
    }

    @Override
    public User read(int id) {
        return userDao.read(id);
    }

    @Override
    public User update(User user) {
        if (userDao.read(user.getId()) == null) {
            return null;
        }
        userDao.update(user);
        return userDao.read(user.getId());
    }

    @Override
    public User delete(int id) {
        User user = userDao.read(id);
        if (user == null) {
            return null;
        }
        userDao.delete(id);
        return user;
    }
}
