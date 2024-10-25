package com.example.demo.service;

import com.example.demo.auth.JwtUser;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user==null){
            throw  new UsernameNotFoundException("Пользователь не найден");
        }else {
            return new JwtUser(user.getId(), user.getLogin(),user.getLogin(), user.getLogin(), user.getPassword(), null,true );
        }
    }
}
/*(final Long id, final String firstName,
final String lastName,
final String email, final String password,
final Collection<? extends GrantedAuthority> authorities,
final boolean enabled)*/
