package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class Verify {
    
    private int userId;
    private String code;
    private OffsetDateTime timestamp;
}
