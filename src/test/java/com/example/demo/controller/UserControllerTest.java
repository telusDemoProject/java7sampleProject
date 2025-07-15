package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private List<Users> testUsers;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController();
        try {
            java.lang.reflect.Field field = UserController.class.getDeclaredField("userService");
            field.setAccessible(true);
            field.set(userController, userService);
        } catch (Exception e) {}
        testUsers = java.util.Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com")
        );
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsers);
        java.util.List<Users> result = userController.getAllUsers();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        verify(userService).getAllUsers();
    }

    @Test
    public void testAddUser() {
        Users newUser = new Users(null, "Alice Brown", "alice@test.com");
        String result = userController.addUser(newUser);
        assertThat(result).isEqualTo("Users added");
        verify(userService).addUser(newUser);
    }

    @Test
    public void testGetUsersByDomain() {
        java.util.List<Users> domainUsers = java.util.Arrays.asList(testUsers.get(0));
        when(userService.getUsersByDomain("example.com")).thenReturn(domainUsers);
        java.util.List<Users> result = userController.getUsersByDomain("example.com");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("john@example.com");
        verify(userService).getUsersByDomain("example.com");
    }

    @Test
    public void testGetSortedUsers() {
        when(userService.sortUsersBy("name")).thenReturn(testUsers);
        java.util.List<Users> result = userController.getSortedUsers("name");
        assertThat(result).hasSize(2);
        verify(userService).sortUsersBy("name");
    }

    @Test
    public void testGetGroupedByDomain() {
        java.util.Map<String, java.util.List<Users>> groupedUsers = new java.util.HashMap<>();
        groupedUsers.put("example.com", java.util.Arrays.asList(testUsers.get(0)));
        groupedUsers.put("test.com", java.util.Arrays.asList(testUsers.get(1)));
        when(userService.groupByEmailDomain()).thenReturn(groupedUsers);
        java.util.Map<String, java.util.List<Users>> result = userController.getGroupedByDomain();
        assertThat(result).hasSize(2);
        assertThat(result).containsKeys("example.com", "test.com");
        verify(userService).groupByEmailDomain();
    }

    @Test
    public void testSearchByName() {
        java.util.List<Users> searchResults = java.util.Arrays.asList(testUsers.get(0));
        when(userService.searchUsersByName("john")).thenReturn(searchResults);
        java.util.List<Users> result = userController.searchByName("john");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        verify(userService).searchUsersByName("john");
    }
}