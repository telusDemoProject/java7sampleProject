package com.example.demo.integration;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class ApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
        
        // Create test data using Java 8 streams
        List<Users> testUsers = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> new Users(null, "User" + i, "user" + i + "@test.com"))
                .collect(java.util.stream.Collectors.toList());
        
        userRepository.saveAll(testUsers);
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void testAddUser() throws Exception {
        Users newUser = new Users(null, "New User", "newuser@test.com");
        
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Users added"));
        
        assertEquals(6, userRepository.count());
    }

    @Test
    void testGetUsersByDomain() throws Exception {
        mockMvc.perform(get("/users/by-domain")
                .param("domain", "test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void testSortedUsers() throws Exception {
        mockMvc.perform(get("/users/sorted")
                .param("field", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("User1"));
    }

    @Test
    void testGroupedByDomain() throws Exception {
        mockMvc.perform(get("/users/grouped-by-domain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['test.com'].length()").value(5));
    }

    @Test
    void testSearchUsers() throws Exception {
        mockMvc.perform(get("/users/search")
                .param("keyword", "User1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("User1"));
    }

    @Test
    void testFileOperations() throws Exception {
        String filename = "integration-test.txt";
        String content = "Integration test content";
        
        // Create file
        mockMvc.perform(post("/file/create")
                .param("filename", filename)
                .param("content", content))
                .andExpect(status().isOk())
                .andExpect(content().string("File created successfully."));
        
        // Read file
        mockMvc.perform(get("/file/read")
                .param("filename", filename))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
        
        // Delete file
        mockMvc.perform(delete("/file/delete")
                .param("filename", filename))
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully."));
    }
}