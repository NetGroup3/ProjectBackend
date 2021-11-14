package com.example.NetProjectBackend.repositories;

import com.example.NetProjectBackend.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    private UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public List<User> getAll(){
        return this.repository.getAll();
    }

    public User readByEmail(String login){
        return this.repository.readByEmail(login);
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = readByEmail(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), true, true, true, true, new HashSet<>());
    }
}
