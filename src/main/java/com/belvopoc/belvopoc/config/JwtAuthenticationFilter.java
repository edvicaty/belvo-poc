package com.belvopoc.belvopoc.config;

import com.belvopoc.belvopoc.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// RequiredArgsConstructor will provide a constructor for any final field specified in the class
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Use of jwtService for manipulating JWTs
    private final JwtService jwtService;
    // A managed Bean is set for adding the required methods (on the ApplicationConfiguration Class)
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // First thing we do in the internal filter is checking for existing JWT tokens

        // Extract the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // Provide an early return if no authHeader was found or if the authHeader doesn't start with the Bearer string
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT from the authHeader
        final String jwt = authHeader.substring(7);
        // Extract the user email from JWT token
        final String userEmail = jwtService.extractUsername(jwt);

        // If the UserEmail is null or the User is already authenticated, continue to the next filter
        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = (User) this.userDetailsService.loadUserByUsername(userEmail);

        // Provide an early return if the token is not valid
        if (!jwtService.isTokenValid(jwt, user)) {
            filterChain.doFilter(request, response);
            return;
        }

        // If Token is valid, update security Context with a new value
        // Create new token
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // Add details to the token from the request object
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        // update Security Context
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
