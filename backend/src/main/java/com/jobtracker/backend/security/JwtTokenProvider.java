package com.jobtracker.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.nio.charset.StandardCharsets;

// The @Component annotation marks the class as a Spring component, making it eligible
// for autodetection when using annotation-based configuration and classpath scanning.
@Component
public class JwtTokenProvider {
    // The logger is used to log security-related events, such as authentication attempts and
    // JWT token validation failures. It provides a way to monitor the application's security
    // features and to detect potential security issues. The logger is disabled by default,
    // but can be enabled by setting the "logging.level.com.jobtracker.backend.security" property
    // to "DEBUG" or lower in the application.properties file.

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    // The @Value annotation is used to inject the value of a property from the application.properties file.
    // It is used to inject the value of the "jwt.secret" property into the "secretKey" field.
    // The secret key is used to sign the JWT token. It is used to verify the authenticity
    // of the token and to prevent tampering.

    @Value("${jwt.secret}")
    private String secretKey;
    
    // The expiration time is used to set the expiration time of the JWT token.
    // It is used to set the expiration time of the token to prevent it from being used
    // after it has expired.
    
    @Value("${jwt.expiration-ms}")
    private int jwtExpirationMs;
    
    // The generateToken method is used to generate a JWT token.
    // It is used to generate a JWT token for the user.
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS512)
                .compact();
    }
      // Get user ID from JWT token
      public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    
        return claims.getSubject();
    }
    
      // The validateToken method is used to validate a JWT token.
      // It is used to check if the token is valid and if the user is authenticated.
      // The method takes a JWT token as a parameter and returns a boolean value.
      // If the token is valid, the method returns true, otherwise it returns false.
    
      // Validate JWT token
      public boolean validateToken(String authToken) {
        try {
            // The parser is used to parse the JWT token and extract the claims.
            // The parser takes the secret key as a parameter and uses it to verify the token.
            // The parser returns a Claims object, which contains the payload of the token.
            // The Claims object is then used to extract the username from the token.
            // The method returns true if the token is valid, otherwise it returns false.

            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }
}
