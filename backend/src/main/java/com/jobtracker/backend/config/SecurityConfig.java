package com.jobtracker.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.config.Customizer;
/*
 * This SecurityConfig class is used to configure Spring Security to use an in-memory
 * user details manager. It will store the user details in memory and use the
 * provided password encoder to encode the passwords.
 *
 * The configuration is done in the securityFilterChain method, which is annotated
 * with @Bean. This method returns a SecurityFilterChain object, which is used to
 * configure the security filter chain.
 *
 * The security filter chain is configured to permit all requests to the following
 * endpoints:
 * - /swagger-ui/**
 * - /v3/api-docs/**
 * - /swagger-resources/**
 * - /webjars/**
 *
 * All other requests are authenticated using the in-memory user details manager.
 *
 * The in-memory user details manager is configured in the userDetailsService method,
 * which is annotated with @Bean. This method returns an InMemoryUserDetailsManager
 * object, which is used to store the user details in memory.
 *
 * The userDetailsService method creates a single user with the username "admin" and
 * the password "admin". The password is encoded using the password encoder.
 *
 * The password encoder is configured in the passwordEncoder method, which is
 * annotated with @Bean. This method returns a BCryptPasswordEncoder object, which
 * is used to encode the passwords.
 */

 @Configuration
 @EnableWebSecurity
 public class SecurityConfig {
 
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
             .authorizeHttpRequests(auth -> auth
                 .requestMatchers(
                     "/v3/api-docs/**",
                     "/swagger-ui/**",
                     "/swagger-ui.html"
                 ).permitAll()
                 .anyRequest().authenticated()
             )
             .csrf(csrf -> csrf.disable())
             .httpBasic(withDefaults());
 
         return http.build();
     }
     
     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder(); 
     }
     @Bean
public InMemoryUserDetailsManager userDetailsService() {
    String encodedPassword = passwordEncoder().encode("1234");
    UserDetails user = User
        .withUsername("admin")
        .password(encodedPassword)  // {noop} means no password encoding for testing
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
}
 }