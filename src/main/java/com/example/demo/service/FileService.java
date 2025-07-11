package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileService {

    private static final Path BASE_PATH = Paths.get("files");

    public FileService() {
        try {
            Files.createDirectories(BASE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create base directory", e);
        }
    }

    public String createFile(String filename, String content) {
        try {
            Path filePath = BASE_PATH.resolve(filename);
            if (Files.exists(filePath)) {
                return "File already exists.";
            }
            Files.writeString(filePath, content, StandardOpenOption.CREATE_NEW);
            return "File created successfully.";
        } catch (IOException e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    public String readFile(String filename) {
        try {
            Path filePath = BASE_PATH.resolve(filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            return Files.readString(filePath) + "\n";
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        try {
            Path filePath = BASE_PATH.resolve(filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            Files.delete(filePath);
            return "File deleted successfully.";
        } catch (IOException e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
}
