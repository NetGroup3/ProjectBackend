package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.ERole;
import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private OffsetDateTime timestamp;
    private String imageId;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authoritiesR;
    private String status;
    private String role;

    public UserDetailsImpl(int id, String firstname, String lastname, String email, OffsetDateTime timestamp, String imageId, String password, String role, String status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.timestamp = timestamp;
        this.imageId = imageId;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getTimestamp(),
                user.getImageId(),
                user.getPassword(),
                user.getRole(),
                user.getStatus());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritiesR;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public int getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public OffsetDateTime getTimestamp(){
        return timestamp;
    }
    public String getImageId(){
        return imageId;
    }
    public String getStatus(){
        return status;
    }
    public String getRole(){
        return role;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
