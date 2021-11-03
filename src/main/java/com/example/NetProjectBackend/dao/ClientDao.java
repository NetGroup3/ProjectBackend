package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Client;

public interface ClientDao {
    void create(Client client);
    Client read(int id);
    void update(Client client);
    void delete(Client client);
}
