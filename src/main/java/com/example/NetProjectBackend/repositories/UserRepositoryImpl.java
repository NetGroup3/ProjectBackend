package com.example.NetProjectBackend.repositories;

import java.util.List;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.UserListRequest;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) {
        int id = userDao.create(user);
        return userDao.readById(id);
    }

    @Override
    public User readById(int id) {
        return userDao.readById(id);
    }

    @Override
    public User readByEmail(String email) {
        return userDao.readByEmail(email);
    }

    @Override
    public User update(User user) {
        if (userDao.readById(user.getId()) == null) {
            return null;
        }
        userDao.update(user);
        return userDao.readById(user.getId());
    }

    @Override
    public User delete(int id) {
        User user = userDao.readById(id);
        if (user == null) {
            return null;
        }
        userDao.delete(id);
        return user;
    }

    @Override
    public List<User> getAllSuitable(UserListRequest req) {
        List<User> list = userDao.getAllSuitable(req);

        int lastIndex = list.size() - 1;
        int startIndex = Math.abs(req.getPerPage()) * (Math.abs(req.getPageNo()) - 1);
        int endIndex = startIndex + Math.abs(req.getPerPage());

        if (startIndex > lastIndex) {
            return null;
        }
        else if (endIndex > lastIndex + 1) {
            endIndex = lastIndex + 1;
        }

        return list.subList(startIndex, endIndex);
    }
}
