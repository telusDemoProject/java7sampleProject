package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public String authenticate(@RequestParam String username, @RequestParam String password) {
        return Optional.ofNullable(username)
                .filter(u -> !u.isBlank())
                .flatMap(u -> Optional.ofNullable(password)
                        .filter(p -> !p.isBlank())
                        .map(p -> new UsernamePasswordAuthenticationToken(u, p)))
                .map(token -> authenticationManager.authenticate(token))
                .filter(Authentication::isAuthenticated)
                .map(auth -> jwtUtil.generateToken(username))
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user request!"));
    }
}