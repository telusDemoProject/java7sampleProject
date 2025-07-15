package com.example.demo.service;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

public class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @BeforeEach
    public void setUp() {
        fileService = new FileService();
        java.nio.file.Path testFile = java.nio.file.Paths.get("files/" + TEST_FILENAME);
        try { java.nio.file.Files.deleteIfExists(testFile); } catch (Exception ignored) {}
    }

    @AfterEach
    public void tearDown() {
        java.nio.file.Path testFile = java.nio.file.Paths.get("files/" + TEST_FILENAME);
        try { java.nio.file.Files.deleteIfExists(testFile); } catch (Exception ignored) {}
    }

    @Test
    public void testCreateFile() {
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        assertThat(result).isEqualTo("File created successfully.");
        java.nio.file.Path file = java.nio.file.Paths.get("files/" + TEST_FILENAME);
        assertThat(java.nio.file.Files.exists(file)).isTrue();
    }

    @Test
    public void testCreateFileAlreadyExists() {
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        assertThat(result).isEqualTo("File already exists.");
    }

    @Test
    public void testReadFile() {
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        String result = fileService.readFile(TEST_FILENAME);
        assertThat(result).isEqualTo(TEST_CONTENT);
    }

    @Test
    public void testReadFileNotFound() {
        String result = fileService.readFile("nonexistent.txt");
        assertThat(result).isEqualTo("File not found.");
    }

    @Test
    public void testDeleteFile() {
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        String result = fileService.deleteFile(TEST_FILENAME);
        assertThat(result).isEqualTo("File deleted successfully.");
        java.nio.file.Path file = java.nio.file.Paths.get("files/" + TEST_FILENAME);
        assertThat(java.nio.file.Files.exists(file)).isFalse();
    }

    @Test
    public void testDeleteFileNotFound() {
        String result = fileService.deleteFile("nonexistent.txt");
        assertThat(result).isEqualTo("File not found.");
    }
}