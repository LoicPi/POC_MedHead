package com.medhead.msauthorization.service;

import java.util.ArrayList;

import com.medhead.msauthorization.model.UserDAO;
import com.medhead.msauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDAO user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        //Use the following line is password are not encrypted in database.
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return new User (user.getUsername(),
                        user.getPassword(),
                        new ArrayList<>());
    }
}