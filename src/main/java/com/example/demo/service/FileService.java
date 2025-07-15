package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    private static final String BASE_PATH = "files/";

    public FileService() {
        try {
            Files.createDirectories(Paths.get(BASE_PATH));
        } catch (Exception e) {
            throw new RuntimeException("Could not create base directory", e);
        }
    }

    public String createFile(String filename, String content) {
        try {
            Path filePath = Paths.get(BASE_PATH, filename);
            if (Files.exists(filePath)) {
                return "File already exists.";
            }
            Files.writeString(filePath, content, StandardCharsets.UTF_8);
            return "File created successfully.";
        } catch (Exception e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    public String readFile(String filename) {
        try {
            Path filePath = Paths.get(BASE_PATH, filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        try {
            Path filePath = Paths.get(BASE_PATH, filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            Files.deleteIfExists(filePath);
            return "File deleted successfully.";
        } catch (Exception e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
}
