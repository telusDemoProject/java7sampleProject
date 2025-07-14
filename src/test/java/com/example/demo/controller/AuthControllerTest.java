package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Should authenticate successfully")
    void shouldAuthenticateSuccessfully() {
        String username = "user";
        String password = "password";
        String expectedToken = "jwt-token";
        
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);
        
        ResponseEntity<String> result = authController.authenticate(username, password);
        
        assertEquals(expectedToken, result.getBody());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(username);
    }

    @Test
    @DisplayName("Should handle authentication failure")
    void shouldHandleAuthenticationFailure() {
        String username = "user";
        String password = "wrongpassword";
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));
        
        ResponseEntity<String> result = authController.authenticate(username, password);
        
        assertTrue(result.getBody().contains("Authentication failed"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}