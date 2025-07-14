package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Test
    public void testAuthControllerExists() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        AuthController controller = new AuthController(authenticationManager, jwtUtil);
        assertNotNull(controller);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testAuthenticateWithNullDependencies() {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("Invalid user request!"));
        AuthController controller = new AuthController(authenticationManager, jwtUtil);
        controller.authenticate("user", "password");
    }
}