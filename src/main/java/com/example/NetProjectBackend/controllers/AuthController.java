package com.example.NetProjectBackend.controllers;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.pojo.MessageResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.example.NetProjectBackend.jwt.JwtUtils;
import com.example.NetProjectBackend.models.ERole;
import com.example.NetProjectBackend.pojo.JwtResponse;
import com.example.NetProjectBackend.pojo.LoginRequest;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody String  login) {
        System.out.println("LOGIN");
        Gson g = new Gson();
        LoginRequest loginRequest = g.fromJson(login, LoginRequest.class);

        System.out.println(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());
        System.out.println(token);
        Authentication authentication = authenticationManager
                .authenticate(token);
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getStatus(),
                userDetails.getTimestamp(),
                userDetails.getImageId(),
                userDetails.getRole()));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest) {

        if (userRepository.readByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        System.out.println(signupRequest.getPassword());
        userRepository.create(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

}
