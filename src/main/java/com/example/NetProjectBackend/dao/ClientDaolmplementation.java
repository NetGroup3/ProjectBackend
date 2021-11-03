package com.example.NetProjectBackend.dao;

import com.example.NetProjectBackend.models.Client;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientDaolmplementation implements ClientDao {

    private Map<Integer, Client> user;

    public ClientDaolmplementation() {
        user = new LinkedHashMap<>();

    }

    @Override
    public void create(Client client) {
        user.put(client.getId(), client);
    }

    @Override
    public Client read(int id) {
        return user.get(id);
    }

    @Override
    public void update(Client client) {
        user.put(client.getId(), client);
    }

    @Override
    public void delete(Client client) {
        user.remove(client.getId());
    }
}
