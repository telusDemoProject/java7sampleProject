package com.example.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUtil Simple Tests")
class JwtUtilSimpleTest {

    @Test
    @DisplayName("Should create JwtUtil instance")
    void testJwtUtilCreation() {
        JwtUtil jwtUtil = new JwtUtil();
        assertNotNull(jwtUtil);
    }

    @Test
    @DisplayName("Should generate non-null token")
    void testGenerateTokenNotNull() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        
        assertAll(
            () -> assertNotNull(token),
            () -> assertFalse(token.isEmpty()),
            () -> assertTrue(token.contains("."))
        );
    }

    @Test
    @DisplayName("Should extract username from token")
    void testExtractUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals(username, extractedUsername);
    }

    @Test
    @DisplayName("Should validate token")
    void testTokenValidation() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    @DisplayName("Should generate different tokens for different users")
    void testDifferentUsersGenerateDifferentTokens() {
        JwtUtil jwtUtil = new JwtUtil();
        String token1 = jwtUtil.generateToken("user1");
        String token2 = jwtUtil.generateToken("user2");
        
        assertNotEquals(token1, token2);
    }
}