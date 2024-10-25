package com.example.demo.controllers;

import com.example.demo.auth.JwtProvider;
import com.example.demo.auth.JwtRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest){
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getLogin());
        if (userDetails!= null&& passwordEncoder.matches(jwtRequest.getPassword(), userDetails.getPassword())){
            jwtProvider.generateAccessToken(userDetails);
        }

        //  в какой форме фронт возращает  токен на оответ пароля и passwort
        //


        return null;
    }
}
