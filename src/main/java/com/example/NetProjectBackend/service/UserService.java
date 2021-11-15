package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.services.mail.Mail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private final Mail mail;
    private final UserRepository userRepository;

    public UserService(Mail mail, UserRepository userRepository) {
        this.mail = mail;
        this.userRepository = userRepository;
    }

    /**Sign Up */
    public ResponseEntity<?> create (User user){
        System.out.println(user.toString());
        //move to @Service or elsewhere
        user.setTimestamp(OffsetDateTime.now());

        if(userRepository.readByEmail(user.getEmail())!=null){ //есть ли пользователь с таким имейлом?
            return ResponseEntity.badRequest().build();
        }
        User userCreated = userRepository.create(user);
        if (userCreated == null) {
            return ResponseEntity.badRequest().build(); // если юзер по каким-то причинам не создался
        }
        System.out.println("send mail");
        mail.sendCode("https://ourproject.space/use_code?code=", "308ty397f239uopdh3f9p823dh928dhp1280dfh89ph", user.getEmail());
        return ResponseEntity.noContent().build();
    }

    /** Recovery Password */
    public ResponseEntity<?> recovery (String email){
        System.out.println(email);

        if(userRepository.readByEmail(email) == null){ //проверка на ниличие в бд
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("send mail");
            mail.sendCode("https://ourproject.space/use_code?code=", "308ty397f239uopdh3f9p823dh928dhp1280dfh89ph", email);
        }
        return ResponseEntity.noContent().build();
    }



    public User readByEmail(String login){
        return this.userRepository.readByEmail(login);
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
