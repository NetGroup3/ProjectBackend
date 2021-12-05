package com.example.NetProjectBackend.models.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String timestamp;
    private String imageId;
    private String status;
    private String role;

    public JwtResponseDto(String token, int id, String email, String firstname, String lastname, OffsetDateTime timestamp, String imageId, String status, String role)
    {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.timestamp = timestamp.toString();
        this.imageId = imageId;
        this.status = status;
        this.role = role;
    }

}
