package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceParameterizedTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        testUsers = List.of(
            new Users(1L, "Alice Johnson", "alice@example.com"),
            new Users(2L, "Bob Smith", "bob@test.com"),
            new Users(3L, "Charlie Brown", "charlie@example.com"),
            new Users(4L, "Diana Prince", "diana@company.org")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"example.com", "test.com", "company.org"})
    void shouldFilterUsersByDomain(String domain) {
        when(userRepository.findAll()).thenReturn(testUsers);

        var result = userService.getUsersByDomain(domain);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(user -> 
            user.getEmail().endsWith("@" + domain)));
    }

    @ParameterizedTest
    @CsvSource({
        "alice, 1",
        "bob, 1", 
        "charlie, 1",
        "johnson, 1",
        "xyz, 0"
    })
    void shouldSearchUsersByKeyword(String keyword, int expectedCount) {
        when(userRepository.findAll()).thenReturn(testUsers);

        var result = userService.searchUsersByName(keyword);

        assertEquals(expectedCount, result.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "email"})
    void shouldSortUsersByField(String field) {
        when(userRepository.findAll()).thenReturn(testUsers);

        var result = userService.sortUsersBy(field);

        assertEquals(testUsers.size(), result.size());
        // Verify sorting worked by checking order
        if ("name".equals(field)) {
            assertEquals("Alice Johnson", result.get(0).getName());
        } else if ("email".equals(field)) {
            assertEquals("alice@example.com", result.get(0).getEmail());
        }
    }
}