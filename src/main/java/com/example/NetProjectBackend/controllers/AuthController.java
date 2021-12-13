package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.exeptions.EmailAlreadyUseException;
import com.example.NetProjectBackend.exeptions.EmailNotFoundException;
import com.example.NetProjectBackend.exeptions.EmptyInputException;
import com.example.NetProjectBackend.models.dto.JwtResponseDto;
import com.example.NetProjectBackend.models.dto.LoginRequestDto;
import com.example.NetProjectBackend.models.dto.MessageResponseDto;
import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import com.example.NetProjectBackend.service.impl.UserServiceImpl;
import com.example.NetProjectBackend.service.jwt.JwtUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userServiceImpl;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequestDto  loginRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponseDto(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getTimestamp(),
                userDetails.getImageId(),
                userDetails.getStatus(),
                userDetails.getRole()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signupRequest) {
        if (userServiceImpl.readByEmail(signupRequest.getEmail()) != null) {
            throw new EmailAlreadyUseException();
        }
        userServiceImpl.create(signupRequest, ERole.USER.getAuthority());
        return new ResponseEntity<String>("User CREATED", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path="/recovery")
    public ResponseEntity<?> recoveryPassword(@RequestBody UserDto email) {
        if(email.getEmail().isEmpty()){
            throw new EmptyInputException("601", "Input param is empty");
        }
        if (userServiceImpl.readByEmail(email.getEmail()) == null){
            throw new EmailNotFoundException();
        }
        return ResponseEntity.ok(userServiceImpl.recovery(email.getEmail()));
    }

    @GetMapping("/code")
    public ResponseEntity<?> code(@RequestParam String param) {
        if(param.isEmpty()){
            throw new EmptyInputException("601", "Input param is empty");
        }
        return ResponseEntity.ok(userServiceImpl.code(param));
    }

}
