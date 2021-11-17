package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.ERole;
import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.services.password.HashPassword;
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
        //user.setPassword(HashPassword.getHashPassword(user.getPassword()));
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
    public User readByName(String name) {

        return userDao.readByName(name);
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

    @Override
    public void changeStatus(EStatus status, int id) {
        userDao.changeStatus(status, id);
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(HashPassword.getHashPassword(password));
        userDao.update(user);
    }


}
