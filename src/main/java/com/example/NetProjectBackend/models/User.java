package com.example.NetProjectBackend.models;


import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    
    private int id;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private OffsetDateTime timestamp;
    private String picture;
    private boolean status;
    private int role;

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
