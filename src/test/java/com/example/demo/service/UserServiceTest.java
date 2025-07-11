package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
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
        @DisplayName("Should get all users")
        void testGetAllUsers() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getAllUsers();
            
            assertEquals(3, result.size());
            verify(userRepository).findAll();
        }

        @Test
        @DisplayName("Should add user")
        void testAddUser() {
            Users newUser = new Users(4L, "Alice Brown", "alice@test.com");
            
            userService.addUser(newUser);
            
            verify(userRepository).save(newUser);
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {
        
        @Test
        @DisplayName("Should get users by domain")
        void testGetUsersByDomain() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getUsersByDomain("example.com");
            
            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(user -> user.getEmail().endsWith("@example.com")));
        }

        @Test
        @DisplayName("Should return empty list for non-existent domain")
        void testGetUsersByDomainNoMatch() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.getUsersByDomain("nonexistent.com");
            
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should search users by name")
        void testSearchUsersByName() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.searchUsersByName("john");
            
            assertEquals(2, result.size());
            assertTrue(result.stream().allMatch(user -> 
                user.getName().toLowerCase().contains("john")));
        }
    }

    @Nested
    @DisplayName("Sorting and Grouping")
    class SortingAndGrouping {
        
        @Test
        @DisplayName("Should sort users by name")
        void testSortUsersByName() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            List<Users> result = userService.sortUsersBy("name");
            
            assertEquals("Bob Johnson", result.get(0).getName());
            assertEquals("Jane Smith", result.get(1).getName());
            assertEquals("John Doe", result.get(2).getName());
        }

        @Test
        @DisplayName("Should group users by email domain")
        void testGroupByEmailDomain() {
            when(userRepository.findAll()).thenReturn(testUsers);
            
            Map<String, List<Users>> result = userService.groupByEmailDomain();
            
            assertEquals(2, result.size());
            assertTrue(result.containsKey("example.com"));
            assertTrue(result.containsKey("test.com"));
            assertEquals(2, result.get("example.com").size());
            assertEquals(1, result.get("test.com").size());
        }
    }
}