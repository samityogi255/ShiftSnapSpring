package com.binaryBaaje.ShiftSnap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.binaryBaaje.ShiftSnap.Service.CustomUserDetailsService;
import com.binaryBaaje.ShiftSnap.jwtconfig.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
            .csrf(customizer-> customizer.disable())
            .authorizeHttpRequests(request -> request
                                                    .requestMatchers("/login","/users")
                                                    .permitAll()
                                                    .anyRequest().authenticated())
        //http.formLogin(org.springframework.security.config.Customizer.withDefaults());
            .httpBasic(org.springframework.security.config.Customizer.withDefaults())
            .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                            .userDetailsService(customUserDetailsService)
                            .passwordEncoder(passwordEncoder())
                            .and()
                            .build();
    }
    
}
