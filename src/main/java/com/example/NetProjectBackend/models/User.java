package com.example.NetProjectBackend.models;


import java.time.OffsetDateTime;
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
    private String picture;
    private String status;
    private int role;

}
