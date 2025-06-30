package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final Path BASE_PATH = Paths.get("files");

    public FileService() {
        try {
            Files.createDirectories(BASE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create files directory", e);
        }
    }

    public String createFile(String filename, String content) {
        try {
            var filePath = BASE_PATH.resolve(filename);
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
            var filePath = BASE_PATH.resolve(filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            return Files.readString(filePath);
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        try {
            var filePath = BASE_PATH.resolve(filename);
            if (!Files.exists(filePath)) {
                return "File not found.";
            }
            Files.delete(filePath);
            return "File deleted successfully.";
        } catch (IOException e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
    
    public List<String> listFiles() {
        try {
            return Files.list(BASE_PATH)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
