package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("FileService Tests")
class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        cleanupTestFile();
    }

    @AfterEach
    void tearDown() {
        cleanupTestFile();
    }

    private void cleanupTestFile() {
        try {
            Path testFile = Paths.get("files", TEST_FILENAME);
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
        void testCreateFile() {
            String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);

            assertEquals("File created successfully.", result);
            assertTrue(Files.exists(Paths.get("files", TEST_FILENAME)));
        }

        @Test
        @DisplayName("Should not create file if already exists")
        void testCreateFileAlreadyExists() {
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);

            String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);

            assertEquals("File already exists.", result);
        }
    }

    @Nested
    @DisplayName("File Reading")
    class FileReading {

        @Test
        @DisplayName("Should read file content")
        void testReadFile() {
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);

            String result = fileService.readFile(TEST_FILENAME);

            assertEquals(TEST_CONTENT + "\n", result);
        }

        @Test
        @DisplayName("Should return error for non-existent file")
        void testReadFileNotFound() {
            String result = fileService.readFile("nonexistent.txt");

            assertEquals("File not found.", result);
        }
    }

    @Nested
    @DisplayName("File Deletion")
    class FileDeletion {

        @Test
        @DisplayName("Should delete file successfully")
        void testDeleteFile() {
            fileService.createFile(TEST_FILENAME, TEST_CONTENT);

            String result = fileService.deleteFile(TEST_FILENAME);

            assertEquals("File deleted successfully.", result);
            assertFalse(Files.exists(Paths.get("files", TEST_FILENAME)));
        }

        @Test
        @DisplayName("Should return error for non-existent file")
        void testDeleteFileNotFound() {
            String result = fileService.deleteFile("nonexistent.txt");

            assertEquals("File not found.", result);
        }
    }
}