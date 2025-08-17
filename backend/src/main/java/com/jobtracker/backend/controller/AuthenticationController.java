
package com.jobtracker.backend.controller;


import com.jobtracker.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jobtracker.backend.service.CustomUserDetailsService;
import com.jobtracker.backend.dto.JwtAuthenticationResponse;
import com.jobtracker.backend.dto.LoginRequest;
import com.jobtracker.backend.dto.SignUpRequest;
import com.jobtracker.backend.dto.UserDTO;
import java.util.Collections;
import jakarta.validation.Valid;







@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    /**
     * The AuthenticationManager is responsible for validating the username and password.
     * When the user submits their username and password, the AuthenticationManager will
     * verify the credentials and return an Authentication object if the credentials are
     * valid. If the credentials are invalid, the AuthenticationManager will throw an
     * AuthenticationException.
     *
     * The AuthenticationManager is also responsible for setting the SecurityContext with the
     * authenticated user. This is necessary for the security framework to function properly.
     */

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * The authenticateUser method is responsible for authenticating the user.
     * It takes a LoginRequest object as input, which contains the email and password of the user.
     * The method uses the AuthenticationManager to authenticate the user.
     * If the authentication is successful, the method generates a JWT token using the JwtTokenProvider
     * and returns a ResponseEntity with the JWT token.
     * If the authentication fails, the method throws an AuthenticationException.
     */
    
     @PostMapping("/signin")
     public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
         // Authenticate the user
         Authentication authentication = authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(
                 loginRequest.getEmail(),
                 loginRequest.getPassword()
             )
         );
 
         // Set the authentication in the security context
         // The security context is used to store the authenticated user.
         // It is a ThreadLocal object, which means that it is stored in the current thread.
         // The security context is cleared after the request is processed.
         // The security context is used by the security framework to determine if the user is authenticated and what roles the user has.

         SecurityContextHolder.getContext().setAuthentication(authentication);
 
         // Generate JWT token
         String jwt = tokenProvider.generateToken(authentication);
         
         // Return the token in the response
         return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
         
     }
     @PostMapping("/signup")
     public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
         //Check if the user/email already exists
         if(customUserDetailsService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use");
         }
         
  // Create new user's account
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signUpRequest.getEmail());
        userDTO.setName(signUpRequest.getName());
        userDTO.setRoles(Collections.singletonList("USER")); // Default role
        
        // We use CustomUserDetailsService instead of UserDetailsService because we want to handle the password encryption
        // ourselves. If we used UserDetailsService, Spring would handle the password encryption itself, but we want to
        // customize the password encryption.

        UserDTO result = customUserDetailsService.createUser(userDTO, signUpRequest.getPassword());

        return ResponseEntity.ok("User registered successfully");
         
     }

 
      
}