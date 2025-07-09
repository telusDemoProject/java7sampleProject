package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileServiceTest {

    private FileService fileService;
    private static final String TEST_FILENAME = "test-file.txt";
    private static final String TEST_CONTENT = "This is test content";

    @Before
    public void setUp() {
        fileService = new FileService();
        // Clean up any existing test file
        File testFile = new File("files/" + TEST_FILENAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @After
    public void tearDown() {
        // Clean up test file after each test
        File testFile = new File("files/" + TEST_FILENAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCreateFile() throws Exception {
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);

        assertEquals("File created successfully.", result);
        assertTrue(Files.exists(Paths.get("files/" + TEST_FILENAME)));
    }

    @Test
    public void testCreateFileAlreadyExists() throws Exception {
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        String result = fileService.createFile(TEST_FILENAME, TEST_CONTENT);

        assertEquals("File already exists.", result);
    }

    @Test
    public void testReadFile() throws Exception {
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);
        String content = fileService.readFile(TEST_FILENAME);

        assertTrue(content.contains("test content"));
    }

    @Test
    public void testReadFileNotFound() {
        String result = fileService.readFile("nonexistent.txt");

        assertEquals("File not found.", result);
    }

    @Test
    public void testDeleteFile() {
        // Create file first
        fileService.createFile(TEST_FILENAME, TEST_CONTENT);

        String result = fileService.deleteFile(TEST_FILENAME);

        assertEquals("File deleted successfully.", result);

        File file = new File("files/" + TEST_FILENAME);
        assertFalse(file.exists());
    }

    @Test
    public void testDeleteFileNotFound() {
        String result = fileService.deleteFile("nonexistent.txt");

        assertEquals("File not found.", result);
    }
}