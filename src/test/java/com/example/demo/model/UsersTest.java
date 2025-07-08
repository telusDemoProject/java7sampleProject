package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void shouldCreateUserWithAllFields() {
        var user = new Users(1L, "John Doe", "john@example.com");
        
        assertAll(
            () -> assertEquals(1L, user.getId()),
            () -> assertEquals("John Doe", user.getName()),
            () -> assertEquals("john@example.com", user.getEmail())
        );
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        var user1 = new Users(1L, "John", "john@test.com");
        var user2 = new Users(1L, "John", "john@test.com");
        var user3 = new Users(2L, "Jane", "jane@test.com");

        assertAll(
            () -> assertEquals(user1, user2),
            () -> assertNotEquals(user1, user3),
            () -> assertEquals(user1.hashCode(), user2.hashCode()),
            () -> assertNotEquals(user1.hashCode(), user3.hashCode())
        );
    }

    @Test
    void shouldTestToString() {
        var user = new Users(1L, "Test User", "test@example.com");
        var result = user.toString();
        
        assertAll(
            () -> assertTrue(result.contains("1")),
            () -> assertTrue(result.contains("Test User")),
            () -> assertTrue(result.contains("test@example.com"))
        );
    }
}