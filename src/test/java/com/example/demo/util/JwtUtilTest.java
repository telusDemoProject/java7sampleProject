package com.example.demo.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUtil Tests")

public class JwtUtilTest {

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
        void shouldGenerateValidToken() {
            String token = jwtUtil.generateToken(TEST_USERNAME);
            
            assertAll(
                () -> assertNotNull(token),
                () -> assertFalse(token.isEmpty()),
                () -> assertTrue(token.contains("."))
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"user1", "user2", "admin", "test@example.com"})
        @DisplayName("Should generate tokens for different usernames")
        void shouldGenerateTokensForDifferentUsernames(String username) {
            String token = jwtUtil.generateToken(username);
            
            assertNotNull(token);
            assertFalse(token.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Token Validation")
    class TokenValidation {
        
        @Test
        @DisplayName("Should extract username from token")
        void shouldExtractUsernameFromToken() {
            String token = jwtUtil.generateToken(TEST_USERNAME);
            
            String extractedUsername = jwtUtil.extractUsername(token);
            
            assertEquals(TEST_USERNAME, extractedUsername);
        }

        @Test
        @DisplayName("Should validate token")
        void shouldValidateToken() {
            String token = jwtUtil.generateToken(TEST_USERNAME);
            
            boolean isValid = jwtUtil.isTokenValid(token);
            
            assertTrue(isValid);
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {
        
        @Test
        @DisplayName("Should generate different tokens for different users")
        void shouldGenerateDifferentTokensForDifferentUsers() {
            String token1 = jwtUtil.generateToken("user1");
            String token2 = jwtUtil.generateToken("user2");
            
            assertAll(
                () -> assertNotEquals(token1, token2),
                () -> assertEquals("user1", jwtUtil.extractUsername(token1)),
                () -> assertEquals("user2", jwtUtil.extractUsername(token2))
            );
        }

        @Test
        @DisplayName("Should throw exception for invalid token")
        void shouldThrowExceptionForInvalidToken() {
            String invalidToken = "invalid.token.here";
            
            assertThrows(Exception.class, () -> jwtUtil.extractUsername(invalidToken));
        }
        
        @Test
        @DisplayName("Should return false for invalid token validation")
        void shouldReturnFalseForInvalidTokenValidation() {
            String invalidToken = "invalid.token.here";
            
            boolean isValid = jwtUtil.isTokenValid(invalidToken);
            
            assertFalse(isValid);
        }
    }
}