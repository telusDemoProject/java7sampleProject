package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void testCreateFile() throws Exception {
        String filename = "test.txt";
        String expectedResponse = "File created";
        when(fileService.createFile(filename, "Hello")).thenReturn("File created");
        
        mockMvc.perform(post("/file/create")
                .param("filename", filename)
                .param("content", "Hello")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testReadFile() throws Exception {
        String filename = "test.txt";
        String expectedResponse = "Read success";
        when(fileService.readFile(filename)).thenReturn(expectedResponse);
        
        mockMvc.perform(get("/file/read")
                .param("filename", filename))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testDeleteFile() throws Exception {
        String filename = "test.txt";
        String expectedResult = "File deleted successfully";
        when(fileService.deleteFile(filename)).thenReturn("File deleted successfully");
        
        mockMvc.perform(delete("/file/delete")
                .param("filename", filename))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}
