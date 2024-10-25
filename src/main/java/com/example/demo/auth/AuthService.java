package com.example.demo.auth;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService { // класс для логики аутентификации
    private final UserRepository userRepository;

    public String login (String login , String password) {
        User user = userRepository.findByLogin(login);
        if (user==null) {
            throw new RuntimeException("Пользователь не найден");
        }
        if (!password.equals(user.getPassword())){
            throw new RuntimeException("Неверный пароль");
        }

        return login;
    }
}
