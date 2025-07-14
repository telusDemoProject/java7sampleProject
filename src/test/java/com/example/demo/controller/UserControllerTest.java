package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
public class UserControllerTest {

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
    @DisplayName("Should get all users")
    void shouldGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsers);

        ResponseEntity<List<Users>> result = userController.getAllUsers();

        assertEquals(2, result.getBody().size());
        assertEquals("John Doe", result.getBody().get(0).getName());
        verify(userService).getAllUsers();
    }

    @Test
    @DisplayName("Should add user")
    void shouldAddUser() {
        Users newUser = new Users(null, "Alice Brown", "alice@test.com");
        
        ResponseEntity<String> result = userController.addUser(newUser);
        
        assertEquals("Users added", result.getBody());
        verify(userService).addUser(newUser);
    }

    @Test
    @DisplayName("Should get users by domain")
    void shouldGetUsersByDomain() {
        List<Users> domainUsers = List.of(testUsers.get(0));
        when(userService.getUsersByDomain("example.com")).thenReturn(domainUsers);

        ResponseEntity<List<Users>> result = userController.getUsersByDomain("example.com");

        assertEquals(1, result.getBody().size());
        assertEquals("john@example.com", result.getBody().get(0).getEmail());
        verify(userService).getUsersByDomain("example.com");
    }

    @Test
    @DisplayName("Should get sorted users")
    void shouldGetSortedUsers() {
        when(userService.sortUsersBy("name")).thenReturn(testUsers);

        ResponseEntity<List<Users>> result = userController.getSortedUsers("name");

        assertEquals(2, result.getBody().size());
        verify(userService).sortUsersBy("name");
    }

    @Test
    @DisplayName("Should get grouped users by domain")
    void shouldGetGroupedUsersByDomain() {
        Map<String, List<Users>> groupedUsers = Map.of(
            "example.com", List.of(testUsers.get(0)),
            "test.com", List.of(testUsers.get(1))
        );
        
        when(userService.groupByEmailDomain()).thenReturn(groupedUsers);

        ResponseEntity<Map<String, List<Users>>> result = userController.getGroupedByDomain();

        assertEquals(2, result.getBody().size());
        assertTrue(result.getBody().containsKey("example.com"));
        verify(userService).groupByEmailDomain();
    }

    @Test
    @DisplayName("Should search users by name")
    void shouldSearchUsersByName() {
        List<Users> searchResults = List.of(testUsers.get(0));
        when(userService.searchUsersByName("john")).thenReturn(searchResults);

        ResponseEntity<List<Users>> result = userController.searchByName("john");

        assertEquals(1, result.getBody().size());
        assertEquals("John Doe", result.getBody().get(0).getName());
        verify(userService).searchUsersByName("john");
    }
}