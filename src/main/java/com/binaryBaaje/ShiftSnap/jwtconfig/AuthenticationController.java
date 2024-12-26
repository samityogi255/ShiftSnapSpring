package com.binaryBaaje.ShiftSnap.jwtconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                            authRequest.getEmail(), authRequest.getPassword()
                                        )
        );
    SecurityContextHolder.getContext().setAuthentication(authentication);//line sets the authenticated user in the security context. 
    //This ensures that the user is considered authenticated for the duration of the request.
    String token = jwtUtil.generateToken(authRequest.getEmail());//After successful authentication, the controller generates a JWT token
    // using the jwtUtil.generateToken() method, passing the user's email (which is used as the subject of the JWT). 
    //The token represents the user's authentication and authorization state.
    return ResponseEntity.ok(new AuthResponse(token));    
    
}

}

