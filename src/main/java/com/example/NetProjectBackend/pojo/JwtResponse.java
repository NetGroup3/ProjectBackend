package com.example.NetProjectBackend.pojo;

import java.time.OffsetDateTime;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String status;
    private OffsetDateTime timestamp;
    private String imageId;
    private String role;

    public JwtResponse(String token, int id, String email, String firstname, String lastname, String status,  OffsetDateTime timestamp, String imageId,String role)
    {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.timestamp = timestamp;
        this.imageId = imageId;
        this.status = status;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
