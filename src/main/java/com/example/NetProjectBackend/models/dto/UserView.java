package com.example.NetProjectBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private OffsetDateTime timestamp;
    private String imageId;
    private String status;
    private String role;
}
