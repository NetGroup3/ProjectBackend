package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.jwt.JwtUtils;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.pojo.JwtResponse;
import com.example.NetProjectBackend.pojo.LoginRequest;
import com.example.NetProjectBackend.pojo.MessageResponse;
import com.example.NetProjectBackend.repositories.UserRepository;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
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

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
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
