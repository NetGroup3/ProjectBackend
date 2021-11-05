package com.example.NetProjectBackend.models;

public class User {
    
    int id;
    String email;
    String nickname;
    String name;
    String surname;
    String role;
    String password;

    User(int id, String email, String nickname, String name, String surname, String role, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.password = password;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
