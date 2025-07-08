package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.example.demo.model.Users;

public class UserServiceSimpleTest {

    @Test
    public void testGetUsersByDomainLogic() {
        List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com"));

        String domain = "example.com";
        List<Users> result = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals("bob@example.com", result.get(1).getEmail());
    }

    @Test
    public void testSortUsersByNameLogic() {
        List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Alice Smith", "alice@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com"));

        users.sort(Comparator.comparing(Users::getName));

        assertEquals("Alice Smith", users.get(0).getName());
        assertEquals("Bob Johnson", users.get(1).getName());
        assertEquals("John Doe", users.get(2).getName());
    }

    @Test
    public void testGroupByEmailDomainLogic() {
        List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com"));

        Map<String, List<Users>> map = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .collect(Collectors.groupingBy(u -> u.getEmail().split("@")[1]));

        assertEquals(2, map.size());
        assertTrue(map.containsKey("example.com"));
        assertTrue(map.containsKey("test.com"));
        assertEquals(2, map.get("example.com").size());
        assertEquals(1, map.get("test.com").size());
    }

    @Test
    public void testSearchUsersByNameLogic() {
        List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com"));

        String keyword = "john";
        List<Users> result = users.stream()
                .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        assertTrue(result.get(0).getName().toLowerCase().contains("john"));
        assertTrue(result.get(1).getName().toLowerCase().contains("john"));
    }
}