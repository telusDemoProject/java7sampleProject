package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Test
    @DisplayName("Should create AuthController instance")
    void testAuthControllerExists() {
        AuthenticationManager authManager = null;
        JwtUtil jwtUtil = new JwtUtil();
        AuthController controller = new AuthController(authManager, jwtUtil);
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Should throw exception with null dependencies")
    void testAuthenticateWithNullDependencies() {
        AuthController controller = new AuthController(null, null);
        
        assertThrows(NullPointerException.class, () -> {
            controller.authenticate("user", "password");
        });
    }
}