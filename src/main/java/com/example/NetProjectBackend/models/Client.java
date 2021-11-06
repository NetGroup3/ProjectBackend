package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class Client {
    private int id;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Time timestamp;
    private String picture;
    private boolean status;

}
