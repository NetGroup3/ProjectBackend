package com.example.NetProjectBackend.controllers;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.servlet.headers.HeadersSecurityMarker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.Header;
import java.nio.charset.Charset;
import java.util.*;


@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    private final UserRepository userRepository;  //replace with @Service layer

    LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping( path = "/login")
    public ResponseEntity<User> getUserByEmail(@RequestHeader(value = "Authorization") String authHeader) {
        System.out.println("login");
        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("Authorization", authHeader);
        System.out.println(authHeader);
        String decoded = new String(Base64.getDecoder().decode(authHeader));

        String[] subStr;
        subStr = decoded.split(":");
        System.out.println(subStr);
        User user = userRepository.readByEmail(subStr[0]);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        else if(Objects.equals(user.getPassword(), subStr[1])){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }
//    @PostMapping (path = "/login")
//    public @ResponseBody User getAuthUser() {
//        System.out.println("login");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            return null;
//        }
//        Object principal = auth.getPrincipal();
//        User user = (principal instanceof User) ? (User) principal : null;
//        return Objects.nonNull(user) ? this.userRepository.readByEmail(user.getEmail()) : null;
//    }
}
