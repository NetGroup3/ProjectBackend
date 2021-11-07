package com.example.NetProjectBackend.models;

import java.sql.Time;

public class User {
    
    int id;
    String nickname;
    String password;
    String firstname;
    String lastname;
    String email;
    Time timestamp;
    String picture;
    boolean status;
    int role;


    public User(int id, String nickname, String password, String firstname, String lastname, String email, Time timestamp, String picture, boolean status, int role) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.timestamp = timestamp;
        this.picture = picture;
        this.status = status;
        this.role = role;

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
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPicture () {
        return this.picture;
    }

}
