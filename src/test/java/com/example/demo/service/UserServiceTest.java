package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        testUsers = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperations {
        
        @Test
        @DisplayName("Should return all users")
        void shouldReturnAllUsers() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getAllUsers();
            
            assertEquals(3, result.size());
            verify(userRepository).findAll();
        }

        @Test
        @DisplayName("Should add user successfully")
        void shouldAddUser() {
            Users newUser = new Users(4L, "Alice Brown", "alice@test.com");
            
            userService.addUser(newUser);
            
            verify(userRepository).save(newUser);
        }
    }

    @Nested
    @DisplayName("Domain Operations")
    class DomainOperations {
        
        @Test
        @DisplayName("Should filter users by domain")
        void shouldFilterUsersByDomain() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getUsersByDomain("example.com");
            
            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(u -> u.getEmail().endsWith("@example.com")));
        }

        @Test
        @DisplayName("Should return empty list for non-existent domain")
        void shouldReturnEmptyForNonExistentDomain() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getUsersByDomain("nonexistent.com");
            
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Sorting Operations")
    class SortingOperations {
        
        @Test
        @DisplayName("Should sort users by name")
        void shouldSortUsersByName() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.sortUsersBy("name");
            
            assertEquals("Bob Johnson", result.get(0).getName());
            assertEquals("Jane Smith", result.get(1).getName());
            assertEquals("John Doe", result.get(2).getName());
        }

        @Test
        @DisplayName("Should sort users by email")
        void shouldSortUsersByEmail() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.sortUsersBy("email");
            
            assertEquals("bob@example.com", result.get(0).getEmail());
            assertEquals("jane@test.com", result.get(1).getEmail());
            assertEquals("john@example.com", result.get(2).getEmail());
        }
    }

    @Nested
    @DisplayName("Advanced Operations")
    class AdvancedOperations {
        
        @Test
        @DisplayName("Should group users by email domain")
        void shouldGroupUsersByEmailDomain() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            Map<String, List<Users>> result = userService.groupByEmailDomain();
            
            assertEquals(2, result.size());
            assertAll(
                () -> assertTrue(result.containsKey("example.com")),
                () -> assertTrue(result.containsKey("test.com")),
                () -> assertEquals(2, result.get("example.com").size()),
                () -> assertEquals(1, result.get("test.com").size())
            );
        }

        @Test
        @DisplayName("Should search users by name")
        void shouldSearchUsersByName() {
            when(userRepository.findByNameContainingIgnoreCase("john")).thenReturn(
                testUsers.stream().filter(u -> u.getName().toLowerCase().contains("john")).toList()
            );
            
            List<Users> result = userService.searchUsersByName("john");
            
            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(u -> u.getName().toLowerCase().contains("john")));
        }

        @Test
        @DisplayName("Should return empty list when no name matches")
        void shouldReturnEmptyWhenNoNameMatches() {
            when(userRepository.findByNameContainingIgnoreCase("xyz")).thenReturn(List.of());
            
            List<Users> result = userService.searchUsersByName("xyz");
            
            assertTrue(result.isEmpty());
        }
    }
}