package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.dto.UserPaginated;
import com.example.NetProjectBackend.models.dto.UserProfileDto;
import com.example.NetProjectBackend.models.dto.UserSearchDto;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.dto.UserListRequest;

import java.util.List;

public interface UserDao {
    List<UserPaginated> getAllSuitable(UserListRequest req);
    int create(User client);
    User readById(int id);
    User readByEmail(String Email);
    User readByName(String name);
    List<UserSearchDto> readUsers(String name);
    UserProfileDto readUser(int id, boolean checkUser);
    void update(User client);
    void updateFull(User user);
    void updateImageId(int userId, String imageId);
    void delete(int id);
    void changeStatus(EStatus status, int id);
    void updatePassword(String password, int id);
}
