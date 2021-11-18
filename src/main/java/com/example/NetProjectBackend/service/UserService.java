package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.services.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final Mail mail;
    private final UserRepository userRepository;

    public UserService(Mail mail, UserRepository userRepository) {
        this.mail = mail;
        this.userRepository = userRepository;
    }

    /**Sign Up */
    public ResponseEntity<?> create (User user){
        //move to @Service or elsewhere
        user.setTimestamp(OffsetDateTime.now());

        if(userRepository.readByEmail(user.getEmail())!=null){ //есть ли пользователь с таким имейлом?
            return ResponseEntity.badRequest().build();
        }
        User userCreated = userRepository.create(user);
        if (userCreated == null) {
            return ResponseEntity.badRequest().build(); // если юзер по каким-то причинам не создался
        }
        mail.confirmationCode("https://ourproject.space/code?param=", user.getEmail());
        return ResponseEntity.ok(userCreated);
    }

    /** Recovery Password */
    public ResponseEntity<?> recovery (String email){
        if(userRepository.readByEmail(email) == null){ //проверка на ниличие в бд
            return ResponseEntity.notFound().build();
        } else {
            if (!mail.recoveryCode("https://ourproject.space/code?param=", email))
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(200);
    }

    /** Code processing */
    public ResponseEntity<?> code (String param) {
        Verify verify = mail.readByCode(param);
        if (verify == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userRepository.readById(verify.getUserId());

        if (Objects.equals(user.getStatus(), EStatus.ACTIVE.getAuthority())) {
            String newPassword = randomPassword();
            if (mail.sendNewPassword("https://ourproject.space/code?param=", newPassword, user, verify)) {
                userRepository.changePassword(user, newPassword);
            } else {
                mail.confirmationCode("https://ourproject.space/code?param=", user.getEmail());
            }
        } else {
            if (mail.checkData(verify)) {
                userRepository.changeStatus(EStatus.ACTIVE, user.getId());
            } else {
                mail.confirmationCode("https://ourproject.space/code?param=", user.getEmail());
            }

        }
        mail.deleteCode(verify.getUserId());
        return ResponseEntity.ok(200);
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

    private String randomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
