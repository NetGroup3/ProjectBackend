package com.example.NetProjectBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private String status;
    private int limit;
    private int offset;
}
