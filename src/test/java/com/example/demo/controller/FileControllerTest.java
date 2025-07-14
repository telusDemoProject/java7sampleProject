package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileController Tests")
public class FileControllerTest {
    
    @Mock
    private FileService fileService;
    
    @InjectMocks
    private FileController fileController;

    @Test
    @DisplayName("Should create file")
    void shouldCreateFile() {
        String filename = "test.txt";
        String expectedResponse = "File created successfully.";
        when(fileService.createFile(filename, "Hello")).thenReturn(expectedResponse);
        
        ResponseEntity<String> result = fileController.createFile(filename, "Hello");
        
        assertEquals(expectedResponse, result.getBody());
        verify(fileService).createFile(filename, "Hello");
    }

    @Test
    @DisplayName("Should read file")
    void shouldReadFile() {
        String filename = "test.txt";
        String expectedResponse = "File content";
        when(fileService.readFile(filename)).thenReturn(expectedResponse);
        
        ResponseEntity<String> result = fileController.readFile(filename);
        
        assertEquals(expectedResponse, result.getBody());
        verify(fileService).readFile(filename);
    }

    @Test
    @DisplayName("Should delete file")
    void shouldDeleteFile() {
        String filename = "test.txt";
        String expectedResult = "File deleted successfully.";
        when(fileService.deleteFile(filename)).thenReturn(expectedResult);
        
        ResponseEntity<String> result = fileController.deleteFile(filename);
        
        assertEquals(expectedResult, result.getBody());
        verify(fileService).deleteFile(filename);
    }
}