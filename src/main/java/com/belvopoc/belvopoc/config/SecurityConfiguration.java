package com.belvopoc.belvopoc.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final Filter jwtAuthFilter;
    // Bean on ApplicationConfiguration
    private final AuthenticationProvider authenticationProvider;

    // At startup, Spring will look for the SecurityFilterChain Bean, this is responsible for securing http security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for stateless applications or APIs using token-based authentication.
                .csrf(csrf -> csrf.disable())
                // permitAll on "" request Matchers, any other should be authenticated
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("").permitAll()
                        .anyRequest().authenticated())
                // Session Creation Stateless. Spring will create a new session for each request
                .sessionManagement(sessions -> sessions
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
