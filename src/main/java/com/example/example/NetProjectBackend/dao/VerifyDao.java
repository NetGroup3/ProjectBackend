package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Verify;

public interface VerifyDao {
    void create(Verify ver);
    Verify readById(int userId);

    Verify readByCode(String code);

    void update(Verify ver);
    void delete(int userId);
}
