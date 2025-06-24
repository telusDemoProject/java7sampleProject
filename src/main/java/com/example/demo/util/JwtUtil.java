package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "a9f8b7c6d5e4g3h2i1j0k9l8m7n6o5p4q3r2s1t0u9v8w7x6y5z4!@#";
    private static final int TOKEN_VALIDITY_HOURS = 10;

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(TOKEN_VALIDITY_HOURS, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token) {
        return extractClaim(token, claims -> !claims.getExpiration().before(new Date()));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return Optional.ofNullable(token)
                .map(this::getClaims)
                .map(claimsResolver)
                .orElse(null);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}