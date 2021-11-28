package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.jwt.JwtUtils;
import com.example.NetProjectBackend.models.dto.JwtResponse;
import com.example.NetProjectBackend.models.dto.LoginRequest;
import com.example.NetProjectBackend.models.dto.MessageResponse;
import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.naming.EjbRef;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<?> authUser(@RequestBody String  login) {
        log.info("LOGIN");
        Gson g = new Gson();
        LoginRequest loginRequest = g.fromJson(login, LoginRequest.class);

        log.info(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());
       System.out.println(token);
        log.info(String.valueOf(token));
        Authentication authentication = authenticationManager
                .authenticate(token);
        log.info(String.valueOf(authentication));
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
                userDetails.getRole()
        ));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest) {
        signupRequest.setTimestamp(OffsetDateTime.now());
        if (userDao.readByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userDao.create(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

}
