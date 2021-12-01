package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.dto.JwtResponse;
import com.example.NetProjectBackend.models.dto.LoginRequest;
import com.example.NetProjectBackend.models.dto.MessageResponse;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.service.UserDetailsImpl;
import com.example.NetProjectBackend.service.UserService;
import com.example.NetProjectBackend.service.jwt.JwtUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<?> authUser(@RequestBody String  login) {
        log.info("LOGIN");
        Gson g = new Gson();
        LoginRequest loginRequest = g.fromJson(login, LoginRequest.class);

        log.info(loginRequest.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());
        log.info(String.valueOf(token));
        Authentication authentication = authenticationManager
                .authenticate(token);
        log.info(String.valueOf(authentication));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        log.info(jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info(userDetails.toString());
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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest) {
        if (userService.readByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userService.create(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody String email) {
        return userService.recovery(email);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/code")
    public ResponseEntity<?> code(@RequestParam String param) {
        return userService.code(param);
    }

}
