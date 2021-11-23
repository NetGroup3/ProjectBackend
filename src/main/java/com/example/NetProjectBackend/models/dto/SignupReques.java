package com.example.NetProjectBackend.models.dto;

import java.time.OffsetDateTime;

public class SignupReques {
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String status;
    private String role;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }


    public String getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }
}
