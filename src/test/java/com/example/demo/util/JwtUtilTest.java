package com.example.demo.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUtil Tests")
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String TEST_USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Nested
    @DisplayName("Token Generation")
    class TokenGeneration {
        
        @Test
        @DisplayName("Should generate valid token")
        void testGenerateToken() {
            String token = jwtUtil.generateToken(TEST_USERNAME);
            
            assertAll(
                () -> assertNotNull(token),
                () -> assertFalse(token.isEmpty()),
                () -> assertTrue(token.contains("."))
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"user1", "user2", "admin", "test@example.com"})
        @DisplayName("Should generate different tokens for different users")
        void testGenerateTokenForDifferentUsers(String username) {
            String token = jwtUtil.generateToken(username);
            String extractedUsername = jwtUtil.extractUsername(token);
            
            assertEquals(username, extractedUsername);
        }
    }

    @Nested
    @DisplayName("Token Validation")
    class TokenValidation {
        
        @Test
        @DisplayName("Should validate token")
        void testIsTokenValid() {
            String token = jwtUtil.generateToken(TEST_USERNAME);
            
            assertTrue(jwtUtil.isTokenValid(token));
        }

        @Test
        @DisplayName("Should throw exception for invalid token")
        void testInvalidToken() {
            String invalidToken = "invalid.token.here";
            
            assertThrows(Exception.class, () -> jwtUtil.extractUsername(invalidToken));
        }
    }

    @Test
    @DisplayName("Should extract username from token")
    void testExtractUsername() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals(TEST_USERNAME, extractedUsername);
    }
}