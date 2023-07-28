package com.belvopoc.belvopoc.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// RequiredArgsConstructor will provide a constructor for any final field specified in the class
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Use of jwtService for manipulating JWTs
    private final JwtService jwtService;

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

    }
}
