package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {

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

    @Test
    void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.getAllUsers();
        
        assertEquals(3, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void shouldAddUser() {
        var newUser = new Users(4L, "Alice Brown", "alice@test.com");
        
        userService.addUser(newUser);
        
        verify(userRepository).save(newUser);
    }

    @Test
    void shouldGetUsersByDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.getUsersByDomain("example.com");
        
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> user.getEmail().endsWith("@example.com")));
    }

    @Test
    void shouldReturnEmptyListWhenNoDomainMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.getUsersByDomain("nonexistent.com");
        
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSortUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.sortUsersBy("name");
        
        assertAll(
            () -> assertEquals("Bob Johnson", result.get(0).getName()),
            () -> assertEquals("Jane Smith", result.get(1).getName()),
            () -> assertEquals("John Doe", result.get(2).getName())
        );
    }

    @Test
    void shouldSortUsersByEmail() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.sortUsersBy("email");
        
        assertAll(
            () -> assertEquals("bob@example.com", result.get(0).getEmail()),
            () -> assertEquals("jane@test.com", result.get(1).getEmail()),
            () -> assertEquals("john@example.com", result.get(2).getEmail())
        );
    }

    @Test
    void shouldGroupByEmailDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.groupByEmailDomain();
        
        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.containsKey("example.com")),
            () -> assertTrue(result.containsKey("test.com")),
            () -> assertEquals(2, result.get("example.com").size()),
            () -> assertEquals(1, result.get("test.com").size())
        );
    }

    @Test
    void shouldSearchUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.searchUsersByName("john");
        
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> 
            user.getName().toLowerCase().contains("john")));
    }

    @Test
    void shouldReturnEmptyListWhenNoNameMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        var result = userService.searchUsersByName("xyz");
        
        assertTrue(result.isEmpty());
    }
}