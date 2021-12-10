package com.example.NetProjectBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private int id;
    private String firstName;
    private String lastName;
    private String imageId;
    private OffsetDateTime timestamp;
}
