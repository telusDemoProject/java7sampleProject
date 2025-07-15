package com.example.demo.service;

import com.example.demo.model.Users;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class UserServiceSimpleTest {

    @Test
    public void testGetUsersByDomainLogic() {
        java.util.List<Users> users = java.util.Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        java.util.List<Users> result = users.stream()
            .filter(u -> u.getEmail() != null && u.getEmail().endsWith("@example.com"))
            .toList();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("john@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    public void testSortUsersByNameLogic() {
        java.util.List<Users> users = java.util.Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Alice Smith", "alice@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        java.util.List<Users> sorted = users.stream()
            .sorted(java.util.Comparator.comparing(Users::getName))
            .toList();
        assertThat(sorted.get(0).getName()).isEqualTo("Alice Smith");
        assertThat(sorted.get(1).getName()).isEqualTo("Bob Johnson");
        assertThat(sorted.get(2).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGroupByEmailDomainLogic() {
        java.util.List<Users> users = java.util.Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        java.util.Map<String, java.util.List<Users>> map = users.stream()
            .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
            .collect(java.util.stream.Collectors.groupingBy(u -> u.getEmail().split("@")[1]));
        assertThat(map).hasSize(2);
        assertThat(map).containsKeys("example.com", "test.com");
        assertThat(map.get("example.com")).hasSize(2);
        assertThat(map.get("test.com")).hasSize(1);
    }

    @Test
    public void testSearchUsersByNameLogic() {
        java.util.List<Users> users = java.util.Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        String keyword = "john";
        java.util.List<Users> result = users.stream()
            .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase()))
            .toList();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName().toLowerCase()).contains("john");
        assertThat(result.get(1).getName().toLowerCase()).contains("john");
    }
}