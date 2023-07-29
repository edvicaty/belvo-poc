package com.belvopoc.belvopoc.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Not making the variable final due to potential errors at runtime when using the @Value annotation
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    // Set expiration time to a day
    private final Integer expirationTimeMillis = 1000 * 60 * 60 * 24;

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


    // This method gets a map for the extra claims that we want to provide, It takes a User which implements UserDetails
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                // We are using the email as the Username
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Overloading the method without any extra claims
    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                // We are using the email as the Username
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        // A token will be valid when is not expired and the username on the jwt subject is equal to the username from UserDetails.
        // Also, the signing is getting evaluated when extracting all claims
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

}
