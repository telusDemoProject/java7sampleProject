package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        
        testUsers = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com")
        );
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsers);

        List<Users> result = userController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userService).getAllUsers();
    }

    @Test
    void testAddUser() {
        Users newUser = new Users(null, "Alice Brown", "alice@test.com");
        
        String result = userController.addUser(newUser);
        
        assertEquals("Users added", result);
        verify(userService).addUser(newUser);
    }

    @Test
    void testGetUsersByDomain() {
        List<Users> domainUsers = Arrays.asList(testUsers.get(0));
        when(userService.getUsersByDomain("example.com")).thenReturn(domainUsers);

        List<Users> result = userController.getUsersByDomain("example.com");

        assertEquals(1, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        verify(userService).getUsersByDomain("example.com");
    }

    @Test
    void testGetSortedUsers() {
        when(userService.sortUsersBy("name")).thenReturn(testUsers);

        List<Users> result = userController.getSortedUsers("name");

        assertEquals(2, result.size());
        verify(userService).sortUsersBy("name");
    }

    @Test
    void testGetGroupedByDomain() {
        Map<String, List<Users>> groupedUsers = new HashMap<>();
        groupedUsers.put("example.com", Arrays.asList(testUsers.get(0)));
        groupedUsers.put("test.com", Arrays.asList(testUsers.get(1)));
        
        when(userService.groupByEmailDomain()).thenReturn(groupedUsers);

        Map<String, List<Users>> result = userController.getGroupedByDomain();

        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.containsKey("example.com")),
            () -> assertTrue(result.containsKey("test.com"))
        );
        verify(userService).groupByEmailDomain();
    }

    @Test
    void testSearchByName() {
        List<Users> searchResults = Arrays.asList(testUsers.get(0));
        when(userService.searchUsersByName("john")).thenReturn(searchResults);

        List<Users> result = userController.searchByName("john");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userService).searchUsersByName("john");
    }
}