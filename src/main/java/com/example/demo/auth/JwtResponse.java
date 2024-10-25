package com.example.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse { // класс для хранения access  и refresh токенов,будем возращать в ответ

    private String accessToken;
    private String refreshToken;
}
