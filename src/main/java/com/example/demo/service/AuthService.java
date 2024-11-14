package com.example.demo.service;

import com.example.demo.auth.JwtProvider;
import com.example.demo.auth.Login;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class AuthService { // класс для логики аутентификации
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder ;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    private Map<String, UserDetails> tokenStorage = new ConcurrentHashMap<>();

    public Optional<UserDetails> logout(String authToken) {
        return ofNullable(tokenStorage.remove(authToken));
    }

    public User register(User user) {
         userRepository.findByLogin(user.getLogin()).ifPresent(user1 -> new RuntimeException("Пользователь уже существует"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
       // return "Пользователь успешно зарегистрирован!";
    }

    public boolean isTokenExist (String token) {
        return tokenStorage.get(token)== null;
    }

    public Optional<Login> login(String loginName, String password) {
        UserDetails userDetails = userService.loadUserByUsername(loginName);
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            String token =  jwtProvider.generateAccessToken(userDetails);
            Login login = new Login();
            login.setAuthToken(token);
            tokenStorage.put(token,userDetails);
            return Optional.ofNullable(login);
        }

        return Optional.empty();
    }

}

