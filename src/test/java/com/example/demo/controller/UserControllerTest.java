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

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        testUsers = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com")
        );
    }

    @Test
    void shouldGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsers);

        var result = userController.getAllUsers();

        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertEquals("John Doe", result.get(0).getName())
        );
        verify(userService).getAllUsers();
    }

    @Test
    void shouldAddUser() {
        var newUser = new Users(null, "Alice Brown", "alice@test.com");
        
        var result = userController.addUser(newUser);
        
        assertEquals("Users added", result);
        verify(userService).addUser(newUser);
    }

    @Test
    void shouldGetUsersByDomain() {
        var domainUsers = List.of(testUsers.get(0));
        when(userService.getUsersByDomain("example.com")).thenReturn(domainUsers);

        var result = userController.getUsersByDomain("example.com");

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals("john@example.com", result.get(0).getEmail())
        );
        verify(userService).getUsersByDomain("example.com");
    }

    @Test
    void shouldGetSortedUsers() {
        when(userService.sortUsersBy("name")).thenReturn(testUsers);

        var result = userController.getSortedUsers("name");

        assertEquals(2, result.size());
        verify(userService).sortUsersBy("name");
    }

    @Test
    void shouldGetGroupedByDomain() {
        var groupedUsers = Map.of(
            "example.com", List.of(testUsers.get(0)),
            "test.com", List.of(testUsers.get(1))
        );
        
        when(userService.groupByEmailDomain()).thenReturn(groupedUsers);

        var result = userController.getGroupedByDomain();

        assertAll(
            () -> assertEquals(2, result.size()),
            () -> assertTrue(result.containsKey("example.com"))
        );
        verify(userService).groupByEmailDomain();
    }

    @Test
    void shouldSearchByName() {
        var searchResults = List.of(testUsers.get(0));
        when(userService.searchUsersByName("john")).thenReturn(searchResults);

        var result = userController.searchByName("john");

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals("John Doe", result.get(0).getName())
        );
        verify(userService).searchUsersByName("john");
    }
}