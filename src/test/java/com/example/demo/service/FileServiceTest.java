package com.example.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        // Clean up any existing test file
        var testFile = new File("files/" + TEST_FILENAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up test file after each test
        var testFile = new File("files/" + TEST_FILENAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void shouldCreateFile() {
        var result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        assertEquals("File created successfully.", result);
        
        var file = new File("files/" + TEST_FILENAME);
        assertTrue(file.exists());
    }

    @Test
    void shouldReturnErrorWhenFileAlreadyExists() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        // Try to create same file again
        var result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        assertEquals("File already exists.", result);
    }

    @Test
    void shouldReadFile() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        var result = fileService.readFile(TEST_FILENAME);
        
        assertEquals(TEST_CONTENT, result);
    }

    @Test
    void shouldReturnErrorWhenFileNotFound() {
        var result = fileService.readFile("nonexistent.txt");
        
        assertEquals("File not found.", result);
    }

    @Test
    void shouldDeleteFile() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        var result = fileService.deleteFile(TEST_FILENAME);
        
        assertEquals("File deleted successfully.", result);
        
        var file = new File("files/" + TEST_FILENAME);
        assertFalse(file.exists());
    }

    @Test
    void shouldReturnErrorWhenDeletingNonExistentFile() {
        var result = fileService.deleteFile("nonexistent.txt");
        
        assertEquals("File not found.", result);
    }
}