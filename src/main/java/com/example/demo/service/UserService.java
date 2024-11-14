package com.example.demo.service;

import com.example.demo.auth.JwtUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByLogin(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        } else {
            User user = userOptional.get();
            return new JwtUser(user.getId(), user.getLogin(), user.getLogin(), user.getLogin(), user.getPassword(), null, true);
        }
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);

    }
}
/*(final Long id, final String firstName,
final String lastName,
final String email, final String password,
final Collection<? extends GrantedAuthority> authorities,
final boolean enabled)*/
