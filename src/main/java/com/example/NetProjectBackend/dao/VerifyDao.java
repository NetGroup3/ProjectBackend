package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Verify;

public interface VerifyDao {
    void create(Verify ver);
    Verify read(int userId);
    void update(Verify ver);
    void delete(int userId);
}
