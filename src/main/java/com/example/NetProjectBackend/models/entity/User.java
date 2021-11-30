package com.example.NetProjectBackend.models.entity;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.NetProjectBackend.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private OffsetDateTime timestamp;
    private String imageId;
    private String status;
    private String role;


    public Set<ERole> getRoles(){
        Set<ERole> roles =new HashSet<>();
        if(Objects.equals(this.getRole(), ERole.USER.getAuthority())){
            roles.add(ERole.USER);
        }
        else if(Objects.equals(this.getRole(), ERole.MODERATOR.getAuthority())){
            roles.add(ERole.MODERATOR);
        }
        else if(Objects.equals(this.getRole(), ERole.ADMIN.getAuthority())){
            roles.add(ERole.ADMIN);
        }
        return roles;
    }
}
