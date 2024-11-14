package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.github.dockerjava.api.exception.UnauthorizedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Optional;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testPassword");
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        UserService userService = new UserService(userRepository);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
    }
}



