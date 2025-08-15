package com.jobtracker.backend.service;

import com.jobtracker.backend.entity.User;
import com.jobtracker.backend.repository.UserRepository;
import com.jobtracker.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jobtracker.backend.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> 
                new UsernameNotFoundException("User not found with email: " + email)
            );
        
        return new UserPrincipal(user); // Create a UserPrincipal object from the User object
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(UUID id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> 
                new UsernameNotFoundException("User not found with id: " + id)
            );
            
        return new UserPrincipal(user);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public UserDTO createUser(UserDTO userDTO, String password) {
        // Create a new User object from the UserDTO object
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        // Set user roles from DTO or default to USER role
        List<User.Role> roles = new ArrayList<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            // Convert string roles to User.Role enum
            for (String roleStr : userDTO.getRoles()) {
                try {
                    roles.add(User.Role.valueOf(roleStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    // If role is invalid, default to USER
                    roles.add(User.Role.USER);
                }
            }
        } else {
            // Default to USER role if no roles are provided
            roles.add(User.Role.USER);
        }
        user.setRole(roles);
        
        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        
        // Save the user to the database
        User savedUser = userRepository.save(user);
        
        // Create a new UserDTO object from the saved User object
        UserDTO result = new UserDTO();
        result.setId(savedUser.getId());
        result.setName(savedUser.getName());
        result.setEmail(savedUser.getEmail());
        // Convert List<Role> to List<String> for DTO
        List<String> roleNames = savedUser.getRole().stream()
            .map(Enum::name)
            .collect(Collectors.toList());
        result.setRoles(roleNames);
        
        return result;
    }
}