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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private List<Users> testUsers;

    @BeforeEach
    public void setUp() {
        
        testUsers = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.getAllUsers();
        
        assertEquals(3, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testAddUser() {
        Users newUser = new Users(4L, "Alice Brown", "alice@test.com");
        
        userService.addUser(newUser);
        
        verify(userRepository).save(newUser);
    }

    @Test
    void testGetUsersByDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.getUsersByDomain("example.com");
        
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> user.getEmail().endsWith("@example.com")));
    }

    @Test
    void testGetUsersByDomainNoMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.getUsersByDomain("nonexistent.com");
        
        assertTrue(result.isEmpty());
    }

    @Test
    void testSortUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.sortUsersBy("name");
        
        assertEquals("Bob Johnson", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        assertEquals("John Doe", result.get(2).getName());
    }

    @Test
    void testSortUsersByEmail() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.sortUsersBy("email");
        
        assertEquals("bob@example.com", result.get(0).getEmail());
        assertEquals("jane@test.com", result.get(1).getEmail());
        assertEquals("john@example.com", result.get(2).getEmail());
    }

    @Test
    void testGroupByEmailDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        Map<String, List<Users>> result = userService.groupByEmailDomain();
        
        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.containsKey("example.com")),
            () -> assertTrue(result.containsKey("test.com")),
            () -> assertEquals(2, result.get("example.com").size()),
            () -> assertEquals(1, result.get("test.com").size())
        );
    }

    @Test
    void testSearchUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.searchUsersByName("john");
        
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(user -> 
            user.getName().toLowerCase().contains("john")));
    }

    @Test
    void testSearchUsersByNameNoMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        
        List<Users> result = userService.searchUsersByName("xyz");
        
        assertTrue(result.isEmpty());
    }
}