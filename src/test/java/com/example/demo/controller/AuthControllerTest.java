package com.example.demo.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthControllerTest {

    private AuthController controller;

    @Before
    public void setUp() {
        controller = Mockito.mock(AuthController.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void testAuthControllerExists() {
        assertNotNull(controller);
    }

    @Test(expected = NullPointerException.class)
    public void testAuthenticateWithNullDependencies() {
        controller.authenticate("user", "password");
    }
}