package com.example.NetProjectBackend.models;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Verify {
    
    private int userId;
    private String code;
    private OffsetDateTime timestamp;
}
