package com.example.NetProjectBackend.models;

import com.example.NetProjectBackend.models.enums.EFriendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private int id;
    private int senderId;
    private int recipientId;
    private String status;
    private OffsetDateTime timestamp;
}
