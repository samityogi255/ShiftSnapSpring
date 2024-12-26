package com.binaryBaaje.ShiftSnap.jwtconfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.binaryBaaje.ShiftSnap.Service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{ // Extends Spring's OncePerRequestFilter to ensure the filter runs once per request

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");// Retrieves the Authorization header from the incoming request that contains the JWT token in the format: "Bearer <JWT Token>"
        String username= null; // Initializes a variable to store the extracted username (email)
        String token= null;// Initializes a variable to store the JWT token

        // Check if the Authorization header is present and starts with 'Bearer ' (standard JWT format)
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7); // Extracts the token by removing the "Bearer " prefix
            username = jwtUtil.extractUsername(token); // Extracts the username (email) from the Jwt token using JwtUtil
        }

          // If the username is found and no authentication exists in the context, proceed with authentication
          /*
           SecurityContextHolder.getContext().getAuthentication(): This checks if the user has already been authenticated in the current
           request. If no authentication is found, the code continues to authenticate the user using the JWT token.
           userDetails = customUserDetailsService.loadUserByUsername(username): This loads the user details (such as roles, permissions) 
           from the database (or other data sources) based on the extracted username.
           */
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
             // Loads user details from the database by username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username); 

            //Validate the token and ensure it matches the provided username (email)
            if(jwtUtil.validateToken(token, userDetails.getUsername())){
                // Create a UsernamePasswordAuthenticationToken object to represent the authenticated user
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Set additional details (e.g., IP address, etc.) about the request
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication object in the SecurityContextHolder to mark the user as authenticated for this request
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
         
        }
         // Continue the filter chain to pass the request to the next filter or controller
        filterChain.doFilter(request, response); 
    }
  
}
