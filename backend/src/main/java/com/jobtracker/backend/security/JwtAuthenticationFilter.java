package com.jobtracker.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.jobtracker.backend.service.CustomUserDetailsService;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // @Autowired is a Spring annotation that is used to inject an instance of the
    // dependent class. It marks a constructor, field, setter method or config method
    // as to be autowired by Spring's dependency injection facilities. This is mostly
    // used for beans that are managed by Spring IoC container. It is a part of the
    // Spring Framework's Inversion of Control feature.

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                String username = jwtTokenProvider.getUsernameFromToken(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                //Create authentication object 

                //UsernamePasswordAuthenticationToken is an implementation of the Authentication interface
                //which is used to store the username and password of a user.
                //It is used by the AuthenticationManager to authenticate the user.
                //The first parameter is the principle which is an object that represents the user,
                //the second parameter is the credentials which is the password of the user,
                //and the third parameter is the authorities which are the roles of the user.
                //In this case, we are using the customUserDetailsService to load the user details and
                //use them to create the authentication object.

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
                
                //Set the authentication details
                //WebAuthenticationDetailsSource is a class that is used to store the details of the authentication request.
                //It is used by the AuthenticationManager to authenticate the user.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                //Set the authentication in the security context
                //SecurityContextHolder is a class that is used to store the security context of the current thread.
                //It is used by the AuthenticationManager to authenticate the user.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        //continue with the filter chain
        filterChain.doFilter(request, response);
        
    }


    // Extract JWT from the Authorization header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
