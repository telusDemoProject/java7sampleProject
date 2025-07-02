package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthController authController;

    @Test
    void testSuccessfulAuthentication() {
        // Given
        String username = "testuser";
        String password = "password";
        String expectedToken = "jwt-token";
        
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);
        when(jwtUtil.generateToken(username)).thenReturn(expectedToken);
        
        // When
        ResponseEntity<String> response = authController.authenticate(username, password);
        
        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(username);
    }

    @Test
    void testFailedAuthentication() {
        // Given
        String username = "testuser";
        String password = "wrongpassword";
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));
        
        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            authController.authenticate(username, password);
        });
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtUtil);
    }
}