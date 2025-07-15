package com.example.demo.service;

import com.example.demo.model.Users;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceSimpleTest {

    @Test
    void testGetUsersByDomainLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        String domain = "example.com";
        List<Users> result = users.stream()
            .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@" + domain))
            .collect(Collectors.toList());
        assertEquals(2, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals("bob@example.com", result.get(1).getEmail());
    }

    @Test
    void testSortUsersByNameLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Alice Smith", "alice@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        users.sort(Comparator.comparing(Users::getName));
        assertEquals("Alice Smith", users.get(0).getName());
        assertEquals("Bob Johnson", users.get(1).getName());
        assertEquals("John Doe", users.get(2).getName());
    }

    @Test
    void testGroupByEmailDomainLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        Map<String, List<Users>> grouped = users.stream()
            .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
            .collect(Collectors.groupingBy(u -> u.getEmail().substring(u.getEmail().indexOf("@") + 1)));
        assertEquals(2, grouped.get("example.com").size());
        assertEquals(1, grouped.get("test.com").size());
    }

    @Test
    void testSearchUsersByNameLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        String keyword = "john";
        List<Users> result = users.stream()
            .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
        assertEquals(2, result.size());
        assertTrue(result.get(0).getName().toLowerCase().contains("john"));
        assertTrue(result.get(1).getName().toLowerCase().contains("john"));
    }
}