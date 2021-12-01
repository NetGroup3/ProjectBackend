package com.example.NetProjectBackend.models.dto;

public class LoginRequestDto {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void getUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
