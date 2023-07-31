package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.exception.BelvoException;
import com.belvopoc.belvopoc.model.dto.AuthenticationRequest;
import com.belvopoc.belvopoc.model.dto.AuthenticationResponse;
import com.belvopoc.belvopoc.model.dto.RegisterRequest;
import com.belvopoc.belvopoc.model.Role;
import com.belvopoc.belvopoc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // TODO: add exception handling on register and authenticate methods
    // TODO: add email format validation
    // TODO: add refresh tokens
    // TODO: add MFA support for Belvo API
    // Create User, add it to the database and return token
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userService.save(user);
        if (savedUser == null) {
            throw new BelvoException("Cannot create user, review request arguments");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getEmail())
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If the username and password are correct, generate token
        var user = userService.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getEmail())
                .build();

    }
}
