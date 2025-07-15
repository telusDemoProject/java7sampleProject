package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilSimpleTest {

    @Test
    void testJwtUtilCreation() {
        JwtUtil jwtUtil = new JwtUtil();
        assertNotNull(jwtUtil);
    }

    @Test
    void testGenerateTokenNotNull() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void testExtractUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testTokenValidation() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void testDifferentUsersGenerateDifferentTokens() {
        JwtUtil jwtUtil = new JwtUtil();
        String token1 = jwtUtil.generateToken("user1");
        String token2 = jwtUtil.generateToken("user2");
        assertNotEquals(token1, token2);
    }
}