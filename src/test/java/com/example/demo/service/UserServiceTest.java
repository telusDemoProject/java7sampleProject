package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private List<Users> testUsers;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService();
        // Use reflection to set the mock repository
        try {
            java.lang.reflect.Field field = UserService.class.getDeclaredField("userRepository");
            field.setAccessible(true);
            field.set(userService, userRepository);
        } catch (Exception e) {
            // Handle reflection exception
        }
        
        testUsers = Arrays.asList(
            new Users(1L, "John Doe", "john@example.com"),
            new Users(2L, "Jane Smith", "jane@test.com"),
            new Users(3L, "Bob Johnson", "bob@example.com")
        );
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.getAllUsers();
        assertThat(result).hasSize(3);
        verify(userRepository).findAll();
    }

    @Test
    public void testAddUser() {
        Users newUser = new Users(4L, "Alice Brown", "alice@test.com");
        userService.addUser(newUser);
        verify(userRepository).save(newUser);
    }

    @Test
    public void testGetUsersByDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.getUsersByDomain("example.com");
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("john@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    public void testGetUsersByDomainNoMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.getUsersByDomain("nonexistent.com");
        assertThat(result).isEmpty();
    }

    @Test
    public void testSortUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.sortUsersBy("name");
        assertThat(result.get(0).getName()).isEqualTo("Bob Johnson");
        assertThat(result.get(1).getName()).isEqualTo("Jane Smith");
        assertThat(result.get(2).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testSortUsersByEmail() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.sortUsersBy("email");
        assertThat(result.get(0).getEmail()).isEqualTo("bob@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("jane@test.com");
        assertThat(result.get(2).getEmail()).isEqualTo("john@example.com");
    }

    @Test
    public void testGroupByEmailDomain() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.Map<String, java.util.List<Users>> result = userService.groupByEmailDomain();
        assertThat(result).hasSize(2);
        assertThat(result).containsKeys("example.com", "test.com");
        assertThat(result.get("example.com")).hasSize(2);
        assertThat(result.get("test.com")).hasSize(1);
    }

    @Test
    public void testSearchUsersByName() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.searchUsersByName("john");
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName().toLowerCase()).contains("john");
        assertThat(result.get(1).getName().toLowerCase()).contains("john");
    }

    @Test
    public void testSearchUsersByNameNoMatch() {
        when(userRepository.findAll()).thenReturn(testUsers);
        java.util.List<Users> result = userService.searchUsersByName("xyz");
        assertThat(result).isEmpty();
    }
}