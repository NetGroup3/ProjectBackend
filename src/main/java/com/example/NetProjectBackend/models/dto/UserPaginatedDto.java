package com.example.NetProjectBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaginatedDto extends UserDto{

    private int pagesTotal;

    public UserPaginatedDto(
            int id,
            String firstname,
            String lastname,
            String email,
            String timestamp,
            String imageId,
            String status,
            String role,
            int pagesTotal
    ) {
        super(id, firstname, lastname, email, timestamp, imageId, status, role);
        this.pagesTotal = pagesTotal;
    }

    public static UserPaginatedDto transform(UserPaginated user) {
        if (user == null) return null;

        UserPaginatedDto newUser = new UserPaginatedDto();
        newUser.setId(user.getId());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setTimestamp(user.getTimestamp().toString());
        newUser.setImageId(user.getImageId());
        newUser.setStatus(user.getStatus());
        newUser.setRole(user.getRole());
        newUser.setPagesTotal(user.getPagesTotal());
        return newUser;
    }

    public static List<UserPaginatedDto> transformList(List<UserPaginated> list) {
        if (list == null) return null;

        List<UserPaginatedDto> dtoList = new ArrayList<>();
        for (UserPaginated user : list) {
            dtoList.add(UserPaginatedDto.transform(user));
        }
        return dtoList;
    }
}
