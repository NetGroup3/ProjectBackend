package com.example.NetProjectBackend.dao;

import java.time.OffsetDateTime;

import com.example.NetProjectBackend.models.Verify;

public class VerifyDaoImpl implements VerifyDao{

    private static final String SELECT_BY_ID = "";
    private static final String INSERT = "";
    private static final String UPDATE = "";
    private static final String DELETE = "";

    @Override
    public void create(Verify ver) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Verify read(int userId) {
        // TODO Auto-generated method stub
        return new Verify(51, "newCodeForVerification", OffsetDateTime.now());
    }

    @Override
    public void update(Verify ver) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(int userId) {
        // TODO Auto-generated method stub
        
    }
    
}
