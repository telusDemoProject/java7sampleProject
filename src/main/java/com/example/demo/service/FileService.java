package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

@Service
public class FileService {

    private static final Path BASE_PATH = Paths.get("files");

    public FileService() {
        try {
            if (Files.notExists(BASE_PATH)) {
                Files.createDirectories(BASE_PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create base directory", e);
        }
    }

    public String createFile(String filename, String content) {
        Path filePath = BASE_PATH.resolve(filename);
        if (Files.exists(filePath)) {
            return """
                File already exists.
                """;
        }
        try {
            Files.writeString(filePath, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            return """
                File created successfully.
                """;
        } catch (IOException e) {
            return """
                Error creating file: %s
                """.formatted(e.getMessage());
        }
    }

    public String readFile(String filename) {
        Path filePath = BASE_PATH.resolve(filename);
        if (Files.notExists(filePath)) {
            return """
                File not found.
                """;
        }
        try {
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return """
                Error reading file: %s
                """.formatted(e.getMessage());
        }
    }

    public String deleteFile(String filename) {
        Path filePath = BASE_PATH.resolve(filename);
        if (Files.notExists(filePath)) {
            return """
                File not found.
                """;
        }
        try {
            Files.delete(filePath);
            return """
                File deleted successfully.
                """;
        } catch (IOException e) {
            return """
                Error deleting file: %s
                """.formatted(e.getMessage());
        }
    }
}
