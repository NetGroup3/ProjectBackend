package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Client;

import java.util.Collection;
import java.util.List;

public interface ClientDao {
    List<Client> getAll();
    void create(Client client);
    Client read(int id);
    void update(Client client);
    void delete(int id);
}
