package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AuthControllerTest {

    @Test
    public void testAuthControllerExists() {
        AuthController controller = new AuthController();
        assertThat(controller).isNotNull();
    }

    @Test
    public void testAuthenticateWithNullDependencies() {
        AuthController controller = new AuthController();
        assertThatThrownBy(() -> controller.authenticate("user", "password"))
            .isInstanceOf(NullPointerException.class);
    }
}