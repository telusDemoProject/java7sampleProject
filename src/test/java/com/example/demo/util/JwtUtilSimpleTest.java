package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilSimpleTest {

    @Test
    public void testJwtUtilCreation() {
        JwtUtil jwtUtil = new JwtUtil();
        assertNotNull(jwtUtil);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2"})
    void testGenerateTokenForDifferentUsers(String username) {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(username);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    public void testExtractUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testTokenValidation() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        
        boolean isValid = jwtUtil.isTokenValid(token);
        
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource({"user1,user2", "user2,user3"})
    void testDifferentUsersGenerateDifferentTokens(String username1, String username2) {
        JwtUtil jwtUtil = new JwtUtil();
        String token1 = jwtUtil.generateToken(username1);
        String token2 = jwtUtil.generateToken(username2);
        
        assertNotEquals(token1, token2);
    }
}