package com.example.demo.util;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class JwtUtilSimpleTest {

    @Test
    public void testJwtUtilCreation() {
        JwtUtil jwtUtil = new JwtUtil();
        assertThat(jwtUtil).isNotNull();
    }

    @Test
    public void testGenerateTokenNotNull() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        
        assertThat(token).isNotNull().isNotEmpty().contains(".");
    }

    @Test
    public void testExtractUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String username = "testuser";
        String token = jwtUtil.generateToken(username);
        
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertThat(extractedUsername).isEqualTo(username);
    }

    @Test
    public void testTokenValidation() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        
        boolean isValid = jwtUtil.isTokenValid(token);
        
        assertThat(isValid).isTrue();
    }

    @Test
    public void testDifferentUsersGenerateDifferentTokens() {
        JwtUtil jwtUtil = new JwtUtil();
        String token1 = jwtUtil.generateToken("user1");
        String token2 = jwtUtil.generateToken("user2");
        
        assertThat(token1).isNotEqualTo(token2);
    }
}