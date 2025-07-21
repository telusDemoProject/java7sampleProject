package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;

@Service
public class FileService {

    private static final String BASE_PATH = "files/";

    public FileService() {
        File folder = new File(BASE_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String createFile(String filename, String content) {
        Path filePath = Paths.get(BASE_PATH, filename);
        if (Files.exists(filePath)) {
            return "File already exists.";
        }
        try {
            Files.writeString(filePath, content);
            return "File created successfully.";
        } catch (IOException e) {
            return "Error creating file: " + e.getMessage();
        }
    }

    public String readFile(String filename) {
        Path filePath = Paths.get(BASE_PATH, filename);
        if (!Files.exists(filePath)) {
            return "File not found.";
        }
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public String deleteFile(String filename) {
        Path filePath = Paths.get(BASE_PATH, filename);
        if (!Files.exists(filePath)) {
            return "File not found.";
        }
        try {
            Files.delete(filePath);
            return "File deleted successfully.";
        } catch (IOException e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
}
