package com.example.NetProjectBackend.models.dto;

import com.example.NetProjectBackend.models.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    public UserDto(
            int id,
            String firstname,
            String lastname,
            String email,
            String timestamp,
            String imageId,
            String status,
            String role
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.timestamp = timestamp;
        this.imageId = imageId;
        this.status = status;
        this.role = role;
    }

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
}
