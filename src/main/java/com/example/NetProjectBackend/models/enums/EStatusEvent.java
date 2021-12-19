package com.example.NetProjectBackend.models.enums;

public enum EStatusEvent {
    DECLINE,
    ACTIVE;

    public String getStatus() {
        return name();
    }
}
