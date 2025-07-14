package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class FileService {

    private static final String BASE_PATH = "files/";
    private final Path baseDirectory = Paths.get(BASE_PATH);

    public FileService() {
        try {
            Files.createDirectories(baseDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create base directory", e);
        }
    }

    public String createFile(String filename, String content) {
        Path filePath = baseDirectory.resolve(filename);
        
        try {
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
        Path filePath = baseDirectory.resolve(filename);
        
        try {
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            
            return Files.readString(filePath);
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        Path filePath = baseDirectory.resolve(filename);
        
        try {
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
