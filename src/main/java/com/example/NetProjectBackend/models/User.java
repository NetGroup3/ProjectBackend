package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

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
    private int imageId;
    private String status;
    private String role;
}
