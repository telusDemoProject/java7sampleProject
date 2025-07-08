package com.example.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String TEST_USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void shouldGenerateToken() {
        var token = jwtUtil.generateToken(TEST_USERNAME);
        
        assertAll(
            () -> assertNotNull(token),
            () -> assertFalse(token.isEmpty()),
            () -> assertTrue(token.contains("."))
        );
    }

    @Test
    void shouldExtractUsername() {
        var token = jwtUtil.generateToken(TEST_USERNAME);
        
        var extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals(TEST_USERNAME, extractedUsername);
    }

    @Test
    void shouldValidateToken() {
        var token = jwtUtil.generateToken(TEST_USERNAME);
        
        var isValid = jwtUtil.isTokenValid(token);
        
        assertTrue(isValid);
    }

    @Test
    void shouldGenerateDifferentTokensForDifferentUsers() {
        var token1 = jwtUtil.generateToken("user1");
        var token2 = jwtUtil.generateToken("user2");
        
        assertAll(
            () -> assertNotEquals(token1, token2),
            () -> assertEquals("user1", jwtUtil.extractUsername(token1)),
            () -> assertEquals("user2", jwtUtil.extractUsername(token2))
        );
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        var invalidToken = "invalid.token.here";
        
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(invalidToken));
    }
}