package com.example.demo.service;

import com.example.demo.model.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Logic Tests")
class UserServiceSimpleTest {

    @Test
    @DisplayName("Should filter users by domain using streams")
    void testGetUsersByDomainLogic() {
        List<Users> users = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        String domain = "example.com";
        List<Users> result = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());
        
        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertEquals("john@example.com", result.get(0).getEmail()),
            () -> assertEquals("bob@example.com", result.get(1).getEmail())
        );
    }

    @Test
    @DisplayName("Should sort users by name using streams")
    void testSortUsersByNameLogic() {
        List<Users> users = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Alice Smith", "alice@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        List<Users> sorted = users.stream()
                .sorted(Comparator.comparing(Users::getName))
                .collect(Collectors.toList());
        
        assertAll(
            () -> assertEquals("Alice Smith", sorted.get(0).getName()),
            () -> assertEquals("Bob Johnson", sorted.get(1).getName()),
            () -> assertEquals("John Doe", sorted.get(2).getName())
        );
    }

    @Test
    @DisplayName("Should group users by email domain using streams")
    void testGroupByEmailDomainLogic() {
        List<Users> users = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        Map<String, List<Users>> grouped = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .collect(Collectors.groupingBy(u -> u.getEmail().split("@")[1]));
        
        assertAll(
            () -> assertEquals(2, grouped.size()),
            () -> assertTrue(grouped.containsKey("example.com")),
            () -> assertTrue(grouped.containsKey("test.com")),
            () -> assertEquals(2, grouped.get("example.com").size()),
            () -> assertEquals(1, grouped.get("test.com").size())
        );
    }

    @Test
    @DisplayName("Should search users by name using streams")
    void testSearchUsersByNameLogic() {
        List<Users> users = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        String keyword = "john";
        List<Users> result = users.stream()
                .filter(u -> u.getName() != null && 
                        u.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        
        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.stream().allMatch(u -> 
                    u.getName().toLowerCase().contains("john")))
        );
    }
}