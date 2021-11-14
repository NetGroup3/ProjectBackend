package com.example.NetProjectBackend.services.password;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptHash {
    public String getHashPassword(String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println(hashedPassword);
        return hashedPassword;
    }
}
