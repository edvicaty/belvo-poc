package com.belvopoc.belvopoc.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    // Not making the variable final due to potential errors at runtime when using the @Value annotation
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String extractUsername(String jwt) {
        // We are expecting the subject (username) to be email of the User
        return extractClaim(jwt, Claims::getSubject);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    // Returns various claims as key-value pairs included on the jwt
    private Claims extractAllClaims(String jwt) {
        return Jwts.
                parserBuilder()
                // Get and set Secret Signing Key
                .setSigningKey(getSigningKey())
                .build()
                // Verify signature of the provided jwt using the provided signing key
                .parseClaimsJws(jwt)
                .getBody();
    }

    // Decode and return Secret Signing Key for signature validation
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
