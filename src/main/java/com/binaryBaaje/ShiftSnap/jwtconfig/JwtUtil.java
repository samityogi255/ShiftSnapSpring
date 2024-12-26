package com.binaryBaaje.ShiftSnap.jwtconfig;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    
    private String secret = "";
    

    public JwtUtil(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256"); // Create an instance of KeyGenerator for HMAC-SHA256 algorithm
            SecretKey sk= keyGen.generateKey();   // Generate a SecretKey object
            secret = Base64.getEncoder().encodeToString(sk.getEncoded()); // Encode the generated secret key to Base64 for use as a string 
            // but we cannot use string for jwt signing and validation, we need Key object to do that so we use getSigningKey method to convert
            //string to cryptographic Key object. 
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
    }

    // Method to get the cryptographic signing key for JWT
    private Key getSigningKey(){
        // Convert the Base64-encoded secret string into a cryptographic Key object
    // This key will be used for signing and verifying JWTs
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Creates a JWT for a user (using their email as the subject).
    public String generateToken(String email){
        return Jwts.builder()
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 60*60*60*10))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
    }


    public String extractUsername(String token) { 
        return Jwts.parser()// Create a new JWT parser to parse(decode) and validate the JWT token
                .setSigningKey(getSigningKey())// Set the signing key for validation, which ensures the token is valid and wasn't tampered with
                .build() //Finalizes the configuration of the parser.
                .parseClaimsJws(token) // Decodes and validates the token, returning the claims if the token is valid.
                .getBody()// Get the claims body of the token, which contains the payload data
                .getSubject();// Extract and return the 'subject' field from the payload, which stores the username/email
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }


    
}
