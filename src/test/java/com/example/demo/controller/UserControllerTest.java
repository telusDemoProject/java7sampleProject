package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Mock
    private UserService userService;
    
    private UserController userController;
    private List<Users> testUsers;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
        testUsers = List.of(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com")
        );
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperations {
        
        @Test
        @DisplayName("Should get all users")
        void testGetAllUsers() {
            when(userService.getAllUsers()).thenReturn(testUsers);

            List<Users> result = userController.getAllUsers();

            assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("John Doe", result.get(0).getName())
            );
            verify(userService).getAllUsers();
        }

        @Test
        @DisplayName("Should add user")
        void testAddUser() {
            Users newUser = new Users(null, "Alice Brown", "alice@test.com");
            
            String result = userController.addUser(newUser);
            
            assertEquals("Users added", result);
            verify(userService).addUser(newUser);
        }
    }

    @Nested
    @DisplayName("Filtering and Searching")
    class FilteringAndSearching {
        
        @Test
        @DisplayName("Should get users by domain")
        void testGetUsersByDomain() {
            List<Users> domainUsers = List.of(testUsers.get(0));
            when(userService.getUsersByDomain("example.com")).thenReturn(domainUsers);

            List<Users> result = userController.getUsersByDomain("example.com");

            assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("john@example.com", result.get(0).getEmail())
            );
            verify(userService).getUsersByDomain("example.com");
        }

        @Test
        @DisplayName("Should search users by name")
        void testSearchByName() {
            List<Users> searchResults = List.of(testUsers.get(0));
            when(userService.searchUsersByName("john")).thenReturn(searchResults);

            List<Users> result = userController.searchByName("john");

            assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("John Doe", result.get(0).getName())
            );
            verify(userService).searchUsersByName("john");
        }
    }

    @Nested
    @DisplayName("Sorting and Grouping")
    class SortingAndGrouping {
        
        @Test
        @DisplayName("Should get sorted users")
        void testGetSortedUsers() {
            when(userService.sortUsersBy("name")).thenReturn(testUsers);

            List<Users> result = userController.getSortedUsers("name");

            assertEquals(2, result.size());
            verify(userService).sortUsersBy("name");
        }

        @Test
        @DisplayName("Should get grouped users by domain")
        void testGetGroupedByDomain() {
            Map<String, List<Users>> groupedUsers = Map.of(
                "example.com", List.of(testUsers.get(0)),
                "test.com", List.of(testUsers.get(1))
            );
            
            when(userService.groupByEmailDomain()).thenReturn(groupedUsers);

            Map<String, List<Users>> result = userController.getGroupedByDomain();

            assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.containsKey("example.com"))
            );
            verify(userService).groupByEmailDomain();
        }
    }
}