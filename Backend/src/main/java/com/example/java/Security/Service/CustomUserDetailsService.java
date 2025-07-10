package com.example.java.Security.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.java.Entity.User.User;
import com.example.java.Repository.User.UserRepository;

@Service
public class CustomUserDetailsService implements  UserDetailsService {
     @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
        return new org.springframework.security.core.userdetails.User(
            user.getUserId(),
            user.getPassword(),
            Collections.emptyList() // You can later add roles
        );
    }

}
