package com.example.demo.service;

import com.example.demo.model.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceSimpleTest {

    @Nested
    @DisplayName("Get Users By Domain Logic")
    class GetUsersByDomainLogicTests {
        @ParameterizedTest
        @CsvSource({
            "example.com,2",
            "test.com,1",
            "nonexistent.com,0"
        })
        void testGetUsersByDomainLogicParameterized(String domain, int expectedCount) {
            List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com")
            );
            List<Users> result = new ArrayList<>();
            for (Users u : users) {
                if (u.getEmail() != null && u.getEmail().endsWith("@" + domain)) {
                    result.add(u);
                }
            }
            assertEquals(expectedCount, result.size());
        }
    }

    @Nested
    @DisplayName("Search Users By Name Logic")
    class SearchUsersByNameLogicTests {
        @ParameterizedTest
        @CsvSource({
            "john,2",
            "Jane,1",
            "xyz,0"
        })
        void testSearchUsersByNameLogicParameterized(String keyword, int expectedCount) {
            List<Users> users = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"),
                new Users(3L, "Bob Johnson", "bob@example.com")
            );
            List<Users> result = new ArrayList<>();
            for (Users u : users) {
                if (u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    result.add(u);
                }
            }
            assertEquals(expectedCount, result.size());
        }
    }

    @Test
    public void testSortUsersByNameLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Alice Smith", "alice@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        Collections.sort(users, new Comparator<Users>() {
            public int compare(Users u1, Users u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });
        
        assertEquals("Alice Smith", users.get(0).getName());
        assertEquals("Bob Johnson", users.get(1).getName());
        assertEquals("John Doe", users.get(2).getName());
    }

    @Test
    public void testGroupByEmailDomainLogic() {
        List<Users> users = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        Map<String, List<Users>> map = new HashMap<String, List<Users>>();
        
        for (int i = 0; i < users.size(); i++) {
            Users u = users.get(i);
            String email = u.getEmail();
            if (email != null) {
                String[] parts = email.split("@");
                if (parts.length == 2) {
                    String domain = parts[1];
                    if (!map.containsKey(domain)) {
                        map.put(domain, new ArrayList<Users>());
                    }
                    map.get(domain).add(u);
                }
            }
        }
        
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
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
        
        String keyword = "john";
        List<Users> result = new ArrayList<Users>();
        
        for (int i = 0; i < users.size(); i++) {
            Users u = users.get(i);
            if (u.getName() != null && u.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(u);
            }
        }
        
        assertEquals(2, result.size());
        assertTrue(result.get(0).getName().toLowerCase().contains("john"));
        assertTrue(result.get(1).getName().toLowerCase().contains("john"));
    }
}