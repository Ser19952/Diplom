package com.example.demo.controllers;

import com.example.demo.auth.JwtProvider;
import com.example.demo.auth.JwtRequest;
import com.example.demo.auth.Login;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService, JwtProvider jwtProvider, AuthService authService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody JwtRequest jwtRequest) {
        Optional<Login> loginOptional = authService.login(jwtRequest.getLogin(), jwtRequest.getPassword());
        return loginOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("auth-token") @NonNull String token) {
authService.logout(token);

        return ResponseEntity.ok().build();
    }
}
