package com.example.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @BeforeEach
    void setUp() throws IOException {
        fileService = new FileService();
        // Clean up any existing test file
        Path testFile = Paths.get("files/" + TEST_FILENAME);
        Files.deleteIfExists(testFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test file after each test
        Path testFile = Paths.get("files/" + TEST_FILENAME);
        Files.deleteIfExists(testFile);
    }

    @Test
    void testCreateFile() {
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        assertEquals("File created successfully.", result);
        assertTrue(Files.exists(Paths.get("files/" + TEST_FILENAME)));
    }

    @Test
    void testCreateFileAlreadyExists() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        // Try to create same file again
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        assertEquals("File already exists.", result);
    }

    @Test
    void testReadFile() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        String result = fileService.readFile(TEST_FILENAME);
        
        assertEquals(TEST_CONTENT, result);
    }

    @Test
    void testReadFileNotFound() {
        String result = fileService.readFile("nonexistent.txt");
        
        assertEquals("File not found.", result);
    }

    @Test
    void testDeleteFile() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        
        String result = fileService.deleteFile(TEST_FILENAME);
        
        assertEquals("File deleted successfully.", result);
        assertFalse(Files.exists(Paths.get("files/" + TEST_FILENAME)));
    }

    @Test
    void testDeleteFileNotFound() {
        String result = fileService.deleteFile("nonexistent.txt");
        
        assertEquals("File not found.", result);
    }
}