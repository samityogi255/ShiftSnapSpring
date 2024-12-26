package com.binaryBaaje.ShiftSnap.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.binaryBaaje.ShiftSnap.jpa.UserRepository;
import com.binaryBaaje.ShiftSnap.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> new UsernameNotFoundException("User not found with email" + email));

        return org.springframework.security.core.userdetails.User.builder()
                   .username(user.getEmail())
                   .password(user.getPassword())
                   .build();
        
    }
    
}
