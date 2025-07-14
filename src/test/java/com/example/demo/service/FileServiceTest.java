package com.example.demo.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileService Tests")

public class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        // Clean up any existing test file
        Path testFile = Path.of("files", TEST_FILENAME);
        try {
            Files.deleteIfExists(testFile);
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up test file after each test
        Path testFile = Path.of("files", TEST_FILENAME);
        try {
            Files.deleteIfExists(testFile);
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @Nested
    @DisplayName("File Creation")
    class FileCreation {
        
        @Test
        @DisplayName("Should create file successfully")
        void shouldCreateFileSuccessfully() {
            String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
            
            assertEquals("File created successfully.", result);
            assertTrue(Files.exists(Path.of("files", TEST_FILENAME)));
        }

        @Test
        @DisplayName("Should not create file if already exists")
        void shouldNotCreateFileIfAlreadyExists() {
            // Create file first
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);
            
            // Try to create same file again
            String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
            
            assertEquals("File already exists.", result);
        }
    }

    @Nested
    @DisplayName("File Reading")
    class FileReading {
        
        @Test
        @DisplayName("Should read file content")
        void shouldReadFileContent() {
            // Create file first
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);
            
            String result = fileService.readFile(TEST_FILENAME);
            
            assertEquals(TEST_CONTENT, result);
        }

        @Test
        @DisplayName("Should return error message for non-existent file")
        void shouldReturnErrorForNonExistentFile() {
            String result = fileService.readFile("nonexistent.txt");
            
            assertEquals("File not found.", result);
        }
    }

    @Nested
    @DisplayName("File Deletion")
    class FileDeletion {
        
        @Test
        @DisplayName("Should delete file successfully")
        void shouldDeleteFileSuccessfully() {
            // Create file first
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);
            
            String result = fileService.deleteFile(TEST_FILENAME);
            
            assertEquals("File deleted successfully.", result);
            assertFalse(Files.exists(Path.of("files", TEST_FILENAME)));
        }

        @Test
        @DisplayName("Should return error message for non-existent file")
        void shouldReturnErrorForNonExistentFileDeletion() {
            String result = fileService.deleteFile("nonexistent.txt");
            
            assertEquals("File not found.", result);
        }
    }
}