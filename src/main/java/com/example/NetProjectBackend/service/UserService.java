package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.EStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    int create(User user, String role);
    boolean recovery(String email);
    boolean code(String param);
    UserDetails loadUserByUsername(String login);
    void updatePassword(PasswordChangeRequestDto passwordCR);
    void changeStatus(EStatus status, int id);
    List<UserPaginatedDto> getAllSuitable(UserListRequest req);
    UserDto update(User user);
    UserDto delete(int id);
    UserDto readById(int id);
    UserDto readByEmail(String email);
    void updateUserImage(UserImageDto obj);
    boolean createModerator(User user);
    List<UserSearchDto> searchUsers(String name);
    UserProfileDto searchUser(int id);
    JwtResponseDto authentication(LoginRequestDto loginRequestDto);

}
