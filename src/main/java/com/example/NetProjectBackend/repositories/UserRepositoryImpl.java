package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.ERole;
import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.services.password.BCryptHash;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) {
        // user.setPassword(BCryptHash.getHashPassword(user.getPassword()));
        user.setRole(ERole.USER.name());
        user.setStatus(EStatus.NOT_VERIFY.name());
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
    public List<User> getAll(){
        return userDao.getAll();
    }

}
