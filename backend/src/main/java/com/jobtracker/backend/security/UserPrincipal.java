package com.jobtracker.backend.security;

import com.jobtracker.backend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Objects;

/**
 * This class is used to wrap a User entity and implements the UserDetails interface
 * which is required by Spring Security. It is used to retrieve the user's authorities
 * and check if the user is enabled or not.
 */




public class UserPrincipal implements UserDetails {
    private final User user;
    
    public UserPrincipal(User user) {
        this.user = user;
    }
    
    /**
     * Returns the authorities granted to the user. If the user doesn't have a role, it returns a GrantedAuthority with the role "ROLE_USER".
     * Otherwise, it returns a list of GrantedAuthority objects with the role names prefixed with "ROLE_".
     * 
     * For example, if the user has the role "USER", it will return a list with the element "ROLE_USER".
     * 
     * @return a collection of GrantedAuthority objects
     */

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        return user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
       // Helper method to get the actual User entity
       public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return user.getEmail().equals(that.user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getEmail());

    }
}
