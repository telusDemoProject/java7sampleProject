package com.example.demo.util;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String TEST_USERNAME = "testuser";

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        assertThat(token).isNotNull().isNotEmpty().contains(".");
    }

    @Test
    public void testExtractUsername() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        String extractedUsername = jwtUtil.extractUsername(token);
        assertThat(extractedUsername).isEqualTo(TEST_USERNAME);
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        boolean isValid = jwtUtil.isTokenValid(token);
        assertThat(isValid).isTrue();
    }

    @Test
    public void testGenerateTokenForDifferentUsers() {
        String token1 = jwtUtil.generateToken("user1");
        String token2 = jwtUtil.generateToken("user2");
        assertThat(token1).isNotEqualTo(token2);
        assertThat(jwtUtil.extractUsername(token1)).isEqualTo("user1");
        assertThat(jwtUtil.extractUsername(token2)).isEqualTo("user2");
    }

    @Test
    public void testInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertThatThrownBy(() -> jwtUtil.extractUsername(invalidToken)).isInstanceOf(Exception.class);
    }
}