package com.example.NetProjectBackend.models.dto;

import com.example.NetProjectBackend.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String timestamp;
    private String imageId;
    private String status;
    private String role;

    public static UserDto transform(User user) {
        if (user == null) return null;

        UserDto newUser = new UserDto();
        newUser.setId(user.getId());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setTimestamp(user.getTimestamp().toString());
        newUser.setImageId(user.getImageId());
        newUser.setStatus(user.getStatus());
        newUser.setRole(user.getRole());
        return newUser;
    }

    public static List<UserDto> transformList(List<User> list) {
        if (list == null) return null;

        List<UserDto> dtoList = new ArrayList<>();
        for (User user : list) {
            dtoList.add(UserDto.transform(user));
        }
        return dtoList;
    }
}
