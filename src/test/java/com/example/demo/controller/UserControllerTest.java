package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private List<Users> testUsers;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController();
        try {
            java.lang.reflect.Field field = UserController.class.getDeclaredField("userService");
            field.setAccessible(true);
            field.set(userController, userService);
        } catch (Exception e) {
            // Handle reflection exception
        }
        testUsers = Arrays.asList(
                new Users(1L, "John Doe", "john@example.com"),
                new Users(2L, "Jane Smith", "jane@test.com"));
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(testUsers);
        List<Users> result = userController.getAllUsers();
        assertEquals(testUsers.size(), result.size());
        assertEquals(testUsers.get(0).getName(), result.get(0).getName());
        verify(userService).getAllUsers();
    }

    @Test
    public void testAddUser() {
        Users newUser = new Users(null, "Alice Brown", "alice@test.com");
        String result = userController.addUser(newUser);
        assertEquals("Users added", result);
        verify(userService).addUser(newUser);
    }

    @Test
    public void testGetUsersByDomain() {
        when(userService.getUsersByDomain(anyString())).thenAnswer(invocation -> testUsers.stream()
                .filter(u -> u.getEmail().endsWith("@test.com")).collect(Collectors.toList()));
        List<Users> result = userController.getUsersByDomain("test.com");
        assertTrue(result.stream().allMatch(u -> u.getEmail().endsWith("@test.com")));
    }

    @Test
    public void testGetSortedUsers() {
        when(userService.sortUsersBy("name")).thenReturn(testUsers);
        List<Users> result = userController.getSortedUsers("name");
        assertEquals(2, result.size());
        verify(userService).sortUsersBy("name");
    }

    @Test
    public void testGetGroupedByDomain() {
        Map<String, List<Users>> groupedUsers = new HashMap<String, List<Users>>();
        groupedUsers.put("example.com", Arrays.asList(testUsers.get(0)));
        groupedUsers.put("test.com", Arrays.asList(testUsers.get(1)));

        when(userService.groupByEmailDomain()).thenReturn(groupedUsers);

        Map<String, List<Users>> result = userController.getGroupedByDomain();

        assertEquals(2, result.size());
        assertTrue(result.containsKey("example.com"));
        verify(userService).groupByEmailDomain();
    }

    @Test
    public void testSearchByName() {
        List<Users> searchResults = Arrays.asList(testUsers.get(0));
        when(userService.searchUsersByName("john")).thenReturn(searchResults);

        List<Users> result = userController.searchByName("john");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userService).searchUsersByName("john");
    }
}