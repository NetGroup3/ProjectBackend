package com.example.NetProjectBackend.models;


import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    
    int id;
    String password;
    String firstname;
    String lastname;
    String email;
    OffsetDateTime timestamp;
    String picture;
    boolean status;
    int role;

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
