package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Service
public class FileService {

    private static final Path BASE_PATH = Paths.get("files");

    public FileService() {
        try {
            Files.createDirectories(BASE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory", e);
        }
    }

    public String createFile(String filename, String content) {
        return Optional.of(BASE_PATH.resolve(filename))
                .filter(path -> !Files.exists(path))
                .map(path -> {
                    try {
                        Files.write(path, content.getBytes());
                        return "File created successfully.";
                    } catch (IOException e) {
                        return "Error creating file: " + e.getMessage();
                    }
                })
                .orElse("File already exists.");
    }

    public String readFile(String filename) {
        Path path = BASE_PATH.resolve(filename);
        try {
            return Files.exists(path) 
                    ? String.join("\n", Files.readAllLines(path))
                    : "File not found.";
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        Path path = BASE_PATH.resolve(filename);
        try {
            return Files.deleteIfExists(path) 
                    ? "File deleted successfully." 
                    : "File not found.";
        } catch (IOException e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
}
